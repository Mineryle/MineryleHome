package com.mineryle.auth;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class LoginServlet extends HttpServlet {
    private static final String SECRET_KEY = "super-secret-key";
    private final Gson gson = new Gson();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    	
    	System.out.println("Heard do-post method on login servlet");
        setCorsHeaders(resp);
        
        resp.setContentType("application/json");


        BufferedReader reader = req.getReader();
        Map<String, String> credentials = gson.fromJson(reader, Map.class);
        System.out.println("Credentials at servlet: " + credentials);
        
        String email = credentials.get("email");
        String password = credentials.get("password");
        
        System.out.println("Email & Pass: " + email + " - " + password);

        if ("hughes.brian@company.com".equals(email) && "admin".equals(password)) {
        	System.out.println("login successful");
        	HttpSession session = req.getSession();
            session.setAttribute("username", email);
            String token = JWT.create()
                    .withSubject(email)
                    .withIssuedAt(new Date())
                    .withExpiresAt(new Date(System.currentTimeMillis() + 3600_000))
                    .sign(Algorithm.HMAC256(SECRET_KEY));

            Map<String, String> responseMap = new HashMap<>();
            responseMap.put("accessToken", token);
            responseMap.put("_authenticated", "true");
            responseMap.put("user", "hughes.brian@company.com");
            resp.getWriter().write(gson.toJson(responseMap));
            
            

            
        } else {
        	System.out.println("Could not authenticate user...");
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            resp.getWriter().write("{\"error\":\"Invalid credentials\"}");
        }
    }

    @Override
    protected void doOptions(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        setCorsHeaders(resp);
    }

    private void setCorsHeaders(HttpServletResponse resp) {
    	resp.setHeader("Access-Control-Allow-Origin", "http://localhost:4200");
    	resp.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS");
    	resp.setHeader("Access-Control-Allow-Headers", "Content-Type, Authorization");
    	resp.setHeader("Access-Control-Allow-Credentials", "true");

    }
}
