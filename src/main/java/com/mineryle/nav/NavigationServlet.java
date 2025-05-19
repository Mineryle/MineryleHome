package com.mineryle.nav;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class NavigationServlet extends HttpServlet {

    private static final Gson gson = new Gson();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	
    	System.out.println("The doGet method inside the nav servlet was heard...");

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");


        // Using Gson to build JSON dynamically
        JsonObject navigation = new JsonObject();

        JsonArray navItems = new JsonArray();
        JsonObject dashboard = new JsonObject();
        dashboard.addProperty("id", "dashboards.project");
        dashboard.addProperty("title", "Dashboard");
        dashboard.addProperty("type", "basic");
        dashboard.addProperty("icon", "heroicons_outline:home");
        dashboard.addProperty("link", "/dashboards/project");
        navItems.add(dashboard);
        
        dashboard.addProperty("id", "dashboards.analytics");
        dashboard.addProperty("title", "Analytics");
        dashboard.addProperty("type", "basic");
        dashboard.addProperty("icon", "heroicons_outline:chart-pie");
        dashboard.addProperty("link", "/dashboards/analytics");
        navItems.add(dashboard);

        navigation.add("compact", navItems);
        navigation.add("default", navItems.deepCopy());
        navigation.add("futuristic", navItems.deepCopy());
        navigation.add("horizontal", navItems.deepCopy());
        
        String jsonNavigation = navigation.toString();

        try (PrintWriter out = response.getWriter()) {
            out.print(jsonNavigation);
        }
    }
}
