package com.beanstalk.backend.rest;

import com.beanstalk.backend.model.ChatDTO;
import com.beanstalk.backend.model.MessageDTO;
import com.beanstalk.backend.service.ChatService;
import com.beanstalk.backend.service.MessageService;

import com.beanstalk.backend.util.ReferencedException;
import com.beanstalk.backend.util.ReferencedWarning;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/api/chats", produces = MediaType.APPLICATION_JSON_VALUE)
public class ChatResource {

    private final ChatService chatService;
    private final MessageService messageService;

    public ChatResource(final ChatService chatService, final MessageService messageService) {
        this.chatService = chatService;
        this.messageService = messageService;
    }

    @GetMapping
    public ResponseEntity<List<ChatDTO>> getAllChats() {
        return ResponseEntity.ok(chatService.findAll());
    }

    @GetMapping("/{chatId}")
    public ResponseEntity<ChatDTO> getChat(@PathVariable(name = "chatId") final Integer chatId) {
        return ResponseEntity.ok(chatService.get(chatId));
    }

    @PostMapping
    public ResponseEntity<Integer> createChat(@RequestBody @Valid final ChatDTO chatDTO) {
        final Integer createdChatId = chatService.create(chatDTO);
        return new ResponseEntity<>(createdChatId, HttpStatus.CREATED);
    }

    @PutMapping("/{chatId}")
    public ResponseEntity<Integer> updateChat(@PathVariable(name = "chatId") final Integer chatId,
            @RequestBody @Valid final ChatDTO chatDTO) {
        chatService.update(chatId, chatDTO);
        return ResponseEntity.ok(chatId);
    }

    @DeleteMapping("/{chatId}")
    public ResponseEntity<Void> deleteChat(@PathVariable(name = "chatId") final Integer chatId) {
        final ReferencedWarning referencedWarning = chatService.getReferencedWarning(chatId);
        if (referencedWarning != null) {
            throw new ReferencedException(referencedWarning);
        }
        chatService.delete(chatId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{chatId}/messages")
    public ResponseEntity<List<MessageDTO>> getChatMessages(
            @PathVariable(name = "chatId") final Integer chatId) {
        return ResponseEntity.ok(messageService.findAllByChatId(chatId));
    }

}
