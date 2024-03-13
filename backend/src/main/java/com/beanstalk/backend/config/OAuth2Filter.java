package com.beanstalk.backend.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.filter.OncePerRequestFilter;
import org.json.JSONObject;
import com.beanstalk.backend.repos.UserRepository;
import com.beanstalk.backend.service.UserService;
import com.beanstalk.backend.util.UserUtil;

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
            @NonNull FilterChain filterChain) throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization");
        final String token;
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("{\"error\": \"Unauthorized\"}");
            UserUtil.userName = "";
   
            return;
        }

        token = authHeader.substring(7);
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);

        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<String> restResponse = restTemplate.exchange(
                "https://api.github.com/user", HttpMethod.GET, entity, String.class);

        if (!restResponse.getStatusCode().is2xxSuccessful()) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("{\"error\": \"Unauthorized\"}");
            UserUtil.userName = "";
            return;
        }

        JSONObject jsonObject = new JSONObject(restResponse.getBody());
        UserUtil.userName = jsonObject.getString("login");

        filterChain.doFilter(request, response);
    }
}