package com.minerylehome.auth;

import java.io.IOException;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class SessionActivityFilter extends OncePerRequestFilter {

    private final AuthenticationService authenticationService;

    public SessionActivityFilter(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {
        filterChain.doFilter(request, response);

        HttpSession session = request.getSession(false);
        if (session == null) {
            return;
        }

        Object userSessionId = session.getAttribute(AuthSessionAttributes.USER_SESSION_ID);
        if (!(userSessionId instanceof Long sessionId)) {
            return;
        }

        authenticationService.recordActivity(
                sessionId,
                request.getMethod(),
                request.getRequestURI(),
                response.getStatus());
    }
}
