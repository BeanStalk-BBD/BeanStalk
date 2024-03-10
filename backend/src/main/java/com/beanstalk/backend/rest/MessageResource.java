package com.beanstalk.backend.rest;

import com.beanstalk.backend.model.MessageDTO;
import com.beanstalk.backend.service.MessageService;
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
@RequestMapping(value = "/api/messages", produces = MediaType.APPLICATION_JSON_VALUE)
public class MessageResource {

    private final MessageService messageService;

    public MessageResource(final MessageService messageService) {
        this.messageService = messageService;
    }

    @GetMapping
    public ResponseEntity<List<MessageDTO>> getAllMessages() {
        return ResponseEntity.ok(messageService.findAll());
    }

    @GetMapping("/{messageId}")
    public ResponseEntity<MessageDTO> getMessage(
            @PathVariable(name = "messageId") final Long messageId) {
        return ResponseEntity.ok(messageService.get(messageId));
    }

    @PostMapping
    public ResponseEntity<Long> createMessage(@RequestBody @Valid final MessageDTO messageDTO) {
        final Long createdMessageId = messageService.create(messageDTO);
        return new ResponseEntity<>(createdMessageId, HttpStatus.CREATED);
    }

    @PostMapping("/sendMessage/{recieverID}")
    public ResponseEntity<Long> sendMessage(@RequestBody @Valid final MessageDTO messageDTO, @PathVariable(name="recieverID") final int recieverID) {
        int chatID=messageService.getChatId(messageDTO, recieverID);
        messageDTO.setChat(chatID);
        final Long createdMessageId = messageService.create(messageDTO);
        return new ResponseEntity<>(createdMessageId, HttpStatus.CREATED);
    }

    @PutMapping("/{messageId}")
    public ResponseEntity<Long> updateMessage(
            @PathVariable(name = "messageId") final Long messageId,
            @RequestBody @Valid final MessageDTO messageDTO) {
        messageService.update(messageId, messageDTO);
        return ResponseEntity.ok(messageId);
    }

    @DeleteMapping("/{messageId}")
    public ResponseEntity<Void> deleteMessage(
            @PathVariable(name = "messageId") final Long messageId) {
        messageService.delete(messageId);
        return ResponseEntity.noContent().build();
    }

}
