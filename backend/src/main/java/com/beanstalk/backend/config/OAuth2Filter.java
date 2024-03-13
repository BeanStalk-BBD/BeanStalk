package com.beanstalk.backend.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

@Component
@RequiredArgsConstructor
public class OAuth2Filter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization");
        final String token;
        response.setContentType("application/json"); // Set Content-Type to application/json
        response.setCharacterEncoding("UTF-8"); // Set character encoding to UTF-8
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 401 status code
            response.getWriter().write("{\"error\": \"Unauthorized\"}");
    
            return;
        }

        token = authHeader.substring(7);

        URL url = new URL("https://api.github.com/user");
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");

        con.setRequestProperty("Authorization", "Bearer " + token);
        int status = con.getResponseCode();

        if(status != 200){
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 401 status code
            response.getWriter().write("{\"error\": \"Unauthorized\"}");
        }
        filterChain.doFilter(request, response);
    }
}