package com.beanstalk.frontend.model;

public record UserResponse(Integer userId, String userName, String oauthUserName, String auth0Id) {
    
}
