package com.mineryle.auth;


import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

public class EmbeddedJettyServer {

    public static void main(String[] args) throws Exception {
        // Jetty uses SLF4J for logging — configure via logback.xml in resources

        // Create and configure the Jetty server
        Server server = new Server(8080);

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");

        // Register your servlet
        context.addServlet(new ServletHolder(new LoginServlet()), "/mineryle/api/login");
        context.addServlet(new ServletHolder(new LoginWithTokenServlet()), "/mineryle/api/sign-in-with-token");

        server.setHandler(context);
        server.start();
        System.out.println("Jetty server running at http://localhost:8080/mineyle/api/login");
        server.join();
    }
}
