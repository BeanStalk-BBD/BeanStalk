package com.beanstalk.frontend.model;

import java.time.OffsetDateTime;

public record MessageResponse(Long messageId, String messageContent, OffsetDateTime messageTimeStamp, Integer chat, Integer messageSender) {
    
}
