package com.beanstalk.backend.config;

import org.apache.catalina.connector.Response;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import org.springframework.web.client.RestTemplate;

public class SecurityConfiguration {

    // Given a bearer token, send a getrequest to the server
    // If the token is valid, the server will return a 200 status code
    // If the token is invalid, the server will return a 401 status code
    // If the token is expired, the server will return a 403 status code
    public static HttpStatusCode validateToken(String token) {
        RestTemplate restTemplate = new RestTemplate();
        String url = "https://api.github.com/user";
        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", "application/vnd.github+json");
        headers.set("Authorization", "Bearer " + token);
        headers.set("X-GitHub-Api-Version", "2022-11-28");
        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
        System.out.println(response);
        return response.getStatusCode();
    }

}