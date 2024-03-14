package com.beanstalk.frontend.model;

public record AuthResponse(String client_id, String device_code, String grant_type) {
    
}
