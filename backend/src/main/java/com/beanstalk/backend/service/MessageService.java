package com.beanstalk.backend.service;

import com.beanstalk.backend.domain.Chat;
import com.beanstalk.backend.domain.Message;
import com.beanstalk.backend.domain.User;
import com.beanstalk.backend.model.MessageDTO;
import com.beanstalk.backend.repos.ChatRepository;
import com.beanstalk.backend.repos.MessageRepository;
import com.beanstalk.backend.repos.UserRepository;
import com.beanstalk.backend.util.NotFoundException;

import java.util.ArrayList;
import java.util.List;


import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class MessageService {

    private final MessageRepository messageRepository;
    private final ChatRepository chatRepository;
    private final UserRepository userRepository;

    public MessageService(final MessageRepository messageRepository,
            final ChatRepository chatRepository, final UserRepository userRepository) {
        this.messageRepository = messageRepository;
        this.chatRepository = chatRepository;
        this.userRepository = userRepository;
    }

    public List<MessageDTO> findAll() {
        final List<Message> messages = messageRepository.findAll(Sort.by("messageId"));
        return messages.stream()
                .map(message -> mapToDTO(message, new MessageDTO()))
                .toList();
    }

    public MessageDTO get(final Long messageId) {
        return messageRepository.findById(messageId)
                .map(message -> mapToDTO(message, new MessageDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final MessageDTO messageDTO) {
        final Message message = new Message();
        mapToEntity(messageDTO, message);
        return messageRepository.save(message).getMessageId();
    }

    public void update(final Long messageId, final MessageDTO messageDTO) {
        final Message message = messageRepository.findById(messageId)
                .orElseThrow(NotFoundException::new);
        mapToEntity(messageDTO, message);
        messageRepository.save(message);
    }

    public void delete(final Long messageId) {
        messageRepository.deleteById(messageId);
    }
    public List<MessageDTO> findAllByChatId(final Integer chatId) {
        Chat chat = chatRepository.findById(chatId)
                .orElseThrow(() -> new NotFoundException("chat not found"));
        final List<Message> messages = messageRepository.findAllByChat(chat);
        return messages.stream()
                .map(message -> mapToDTO(message, new MessageDTO()))
                .toList();
    }
    public List<MessageDTO> findTop10ByChatId(final Integer chatId, final Integer pageNumber) {
        Chat chat = chatRepository.findById(chatId)
                .orElseThrow(() -> new NotFoundException("chat not found"));
                Pageable topTen = PageRequest.of(pageNumber, 10); 
        final List<Message> messages = messageRepository.findTop10ByChat(chat, topTen);
        return messages.stream()
                .map(message -> mapToDTO(message, new MessageDTO()))
                .toList();
    }

    private MessageDTO mapToDTO(final Message message, final MessageDTO messageDTO) {
        messageDTO.setMessageId(message.getMessageId());
        messageDTO.setMessageContent(message.getMessageContent());
        messageDTO.setMessageTimeStamp(message.getMessageTimeStamp());
        messageDTO.setChat(message.getChat() == null ? null : message.getChat().getChatId());
        messageDTO.setMessageSender(message.getMessageSender() == null ? null : message.getMessageSender().getUserId());
        return messageDTO;
    }


    private Message mapToEntity(final MessageDTO messageDTO, final Message message) {
        message.setMessageContent(messageDTO.getMessageContent());
        message.setMessageTimeStamp(messageDTO.getMessageTimeStamp());
        final Chat chat = messageDTO.getChat() == null ? null : chatRepository.findById(messageDTO.getChat())
                .orElseThrow(() -> new NotFoundException("chat not found"));
        message.setChat(chat);
        final User messageSender = messageDTO.getMessageSender() == null ? null : userRepository.findById(messageDTO.getMessageSender())
                .orElseThrow(() -> new NotFoundException("messageSender not found"));
        message.setMessageSender(messageSender);
        return message;
    }

    public int getChatId(final MessageDTO messageDTO, int reciever ){
        int sender=messageDTO.getMessageSender();
        return(messageRepository.getChatID(sender, reciever));
    }

    public List<String> getOpenChats(int userId, int pageNumber){
        List<String> ret= new ArrayList<String>();

        return(ret);
    }

}
