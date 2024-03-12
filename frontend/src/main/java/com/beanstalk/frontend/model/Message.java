package com.beanstalk.frontend.model;

import java.time.OffsetDateTime;

public record Message(String messageContent, OffsetDateTime messageTimeStamp, Integer chat, Integer messageSender) {
    
}
