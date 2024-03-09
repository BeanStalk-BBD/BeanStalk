package com.beanstalk.backend.model;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.OffsetDateTime;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class MessageDTO {

    private Integer messageId;

    @NotNull
    @Size(max = 20)
    private String messageContent;

    @NotNull
    private OffsetDateTime messageTimeStamp;

    @NotNull
    private Integer chat;

    @NotNull
    private Integer messageSender;

}
