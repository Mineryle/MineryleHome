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

public class LoginWithTokenServlet extends HttpServlet {
	private static final String SECRET_KEY = "super-secret-key";
	private final Gson gson = new Gson();

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		System.out.println("Heard do-post on token login servlet");
		setCorsHeaders(resp);

		resp.setContentType("application/json");

		BufferedReader reader = req.getReader();
		Map<String, String> credentials = gson.fromJson(reader, Map.class);
		System.out.println("Token credentials: " + credentials);

		String token = credentials.get("accessToken");

		Map<String, String> responseMap = new HashMap<>();
		responseMap.put("accessToken", token);
		responseMap.put("_authenticated", "true");
		responseMap.put("user", "hughes.brian@company.com");
		resp.getWriter().write(gson.toJson(responseMap));

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
