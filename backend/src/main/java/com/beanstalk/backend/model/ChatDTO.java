package com.beanstalk.backend.model;

import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Table(name = "chats")
@Getter
@Setter
public class ChatDTO {

    private Integer chatId;

    @NotNull
    private Integer chatUserId1;

    @NotNull
    private Integer chatUserId2;

}
