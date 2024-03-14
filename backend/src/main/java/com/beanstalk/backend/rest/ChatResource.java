package com.beanstalk.backend.rest;

import com.beanstalk.backend.model.MessageDTO;
import com.beanstalk.backend.service.ChatService;
import com.beanstalk.backend.service.MessageService;
import com.beanstalk.backend.service.UserService;
import com.beanstalk.backend.util.UserUtil;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping(value = "/api/chats", produces = MediaType.APPLICATION_JSON_VALUE)
public class ChatResource {

    private final ChatService chatService;
    private final MessageService messageService;
    private final UserService userService;
    public ChatResource(final ChatService chatService, final MessageService messageService, final UserService userService) {
        this.chatService = chatService;
        this.messageService = messageService;
        this.userService=userService;
    }

    @GetMapping({"/{recieverName}/{senderID}/messages10/{page}","/{recieverName}/{senderID}/messages10"})
    public ResponseEntity<List<MessageDTO>> getChatMessages10(
            @PathVariable(name = "recieverName") final String recieverName, @PathVariable(name="page", required = false) final Integer page, @PathVariable(name="senderID") final Integer senderID) {
                int pageNumber = (page == null) ? 0 : page;
                boolean userExists=userService.userNameExists(recieverName);
                if(!userExists){
                    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                }
                final int recieverID=userService.getUserIDfromUserName(recieverName);
                int clientSenderID=userService.getClientUserID(UserUtil.userName);
                int chatID=messageService.getChatIDfromUserIDs(clientSenderID,recieverID);
                
        return ResponseEntity.ok(messageService.findTop10ByChatId(chatID,pageNumber));
    }

    @GetMapping({"/openchats/{userId}/{page}","/openchats/{userId}"})
    public ResponseEntity<List<String>> getOpenChats(@PathVariable(name="userId") final Integer userId, @PathVariable(name="page",required = false) final Integer page) {
        int pageNumber = (page == null) ? 0 : page;
        int clientUserID=userService.getClientUserID(UserUtil.userName);
        return ResponseEntity.ok(messageService.getOpenChats(clientUserID,pageNumber));
    }

    @GetMapping("/getUserID")
    public ResponseEntity<Integer> getMyUserID(){
        return ResponseEntity.ok(userService.getClientUserID(UserUtil.userName));
    }
    
}
