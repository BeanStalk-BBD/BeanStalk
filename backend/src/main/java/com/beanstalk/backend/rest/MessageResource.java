package com.beanstalk.backend.rest;

import com.beanstalk.backend.model.MessageDTO;
import com.beanstalk.backend.service.MessageService;
import com.beanstalk.backend.service.UserService;
import com.beanstalk.backend.util.UserUtil;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/api/messages", produces = MediaType.APPLICATION_JSON_VALUE)
public class MessageResource {

    private final MessageService messageService;
    private final UserService userService;
    public MessageResource(final MessageService messageService, final UserService userService) {
        this.messageService = messageService;
        this.userService=userService;
    }


   

 

    @PostMapping("/sendMessage/{recieverName}")
    public ResponseEntity<Long> sendMessage(@RequestBody @Valid final MessageDTO messageDTO, @PathVariable(name="recieverName") final String recieverName) {
        boolean userExists=userService.userNameExists(recieverName);
        if(!userExists){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        int recieverID=userService.getUserIDfromUserName(recieverName);
        var clientUserId=userService.getClientUserID(UserUtil.userName);
        messageDTO.setMessageSender(clientUserId);
        int chatID=messageService.getChatId(messageDTO, recieverID);
        messageDTO.setChat(chatID);
        final Long createdMessageId = messageService.create(messageDTO);
        return new ResponseEntity<>(createdMessageId, HttpStatus.CREATED);
    }



}
