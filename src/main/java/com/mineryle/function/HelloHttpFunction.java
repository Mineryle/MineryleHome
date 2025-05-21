package com.mineryle.function;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.sqladmin.SQLAdmin;
import com.google.api.services.sqladmin.SQLAdminScopes;
import com.google.api.services.sqladmin.model.DatabaseInstance;
import com.google.api.services.sqladmin.model.Settings;
import com.google.auth.http.HttpCredentialsAdapter;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.functions.HttpFunction;
import com.google.cloud.functions.HttpRequest;
import com.google.cloud.functions.HttpResponse;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.io.BufferedWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.Duration;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

public class HelloHttpFunction implements HttpFunction {

    private static final String INSTANCE_CONNECTION_NAME = System.getenv("INSTANCE_CONNECTION_NAME");
    private static final String DB_USER = System.getenv("DB_USER");
    private static final String DB_PASS = System.getenv("DB_PASS");
    private static final String DB_NAME = System.getenv("DB_NAME");
    private static final int INACTIVITY_THRESHOLD_MINUTES = 15;

    @Override
    public void service(HttpRequest request, HttpResponse response) throws Exception {
        BufferedWriter writer = response.getWriter();

        GoogleCredentials credentials = GoogleCredentials.getApplicationDefault()
                .createScoped(SQLAdminScopes.CLOUD_PLATFORM);

        SQLAdmin sqlAdmin = new SQLAdmin.Builder(
                GoogleNetHttpTransport.newTrustedTransport(),
                GsonFactory.getDefaultInstance(),
                new HttpCredentialsAdapter(credentials))
                .setApplicationName("cloud-sql-auto-shutdown")
                .build();

        String[] parts = INSTANCE_CONNECTION_NAME.split(":");
        String project = parts[0];
        String instance = parts[2];

        if (!isInstanceRunning(sqlAdmin, project, instance)) {
            writer.write("Instance is already stopped. No action required.");
            return;
        }

        try (HikariDataSource dataSource = createConnectionPool();
             Connection connection = dataSource.getConnection()) {

            String sql = "SELECT MAX(state_change) AS last_activity FROM pg_stat_activity WHERE pid <> pg_backend_pid() AND state <> 'idle'";
            try (PreparedStatement stmt = connection.prepareStatement(sql);
                 ResultSet rs = stmt.executeQuery()) {

                OffsetDateTime lastActivity = null;

                if (rs.next()) {
                    lastActivity = rs.getObject("last_activity", OffsetDateTime.class);
                }

                OffsetDateTime now = OffsetDateTime.now(ZoneOffset.UTC);

                boolean isInactive = (lastActivity == null) ||
                        Duration.between(lastActivity, now).toMinutes() > INACTIVITY_THRESHOLD_MINUTES;

                if (isInactive) {
                    shutdownInstance(sqlAdmin, project, instance);
                    writer.write(String.format("Instance '%s' shut down due to inactivity.", INSTANCE_CONNECTION_NAME));
                } else {
                    writer.write("Instance active. No action taken.");
                }
            }

        } catch (Exception e) {
            writer.write("An error occurred: " + e.getMessage());
            response.setStatusCode(500);
        }
    }

    private HikariDataSource createConnectionPool() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(String.format(
                "jdbc:postgresql:///%s?socketFactory=com.google.cloud.sql.postgres.SocketFactory&cloudSqlInstance=%s",
                DB_NAME, INSTANCE_CONNECTION_NAME));
        config.setUsername(DB_USER);
        config.setPassword(DB_PASS);
        config.setMaximumPoolSize(2);
        config.setMinimumIdle(1);
        config.setConnectionTimeout(10000);
        config.setIdleTimeout(60000);
        return new HikariDataSource(config);
    }

    private boolean isInstanceRunning(SQLAdmin sqlAdmin, String project, String instance) throws IOException {
        DatabaseInstance dbInstance = sqlAdmin.instances().get(project, instance).execute();
        return "RUNNABLE".equalsIgnoreCase(dbInstance.getState());
    }

    private void shutdownInstance(SQLAdmin sqlAdmin, String project, String instance) throws IOException {
        DatabaseInstance dbInstance = sqlAdmin.instances().get(project, instance).execute();
        Settings settings = dbInstance.getSettings();
        settings.setActivationPolicy("NEVER");

        DatabaseInstance updateRequest = new DatabaseInstance();
        updateRequest.setSettings(settings);

        sqlAdmin.instances().patch(project, instance, updateRequest).execute();
    }
} 
