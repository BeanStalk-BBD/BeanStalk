package com.beanstalk.backend.service;

import com.beanstalk.backend.domain.Chat;
import com.beanstalk.backend.domain.Message;
import com.beanstalk.backend.domain.User;
import com.beanstalk.backend.model.ChatDTO;
import com.beanstalk.backend.repos.ChatRepository;
import com.beanstalk.backend.repos.MessageRepository;
import com.beanstalk.backend.repos.UserRepository;
import com.beanstalk.backend.util.NotFoundException;
import com.beanstalk.backend.util.ReferencedWarning;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class ChatService {

    private final ChatRepository chatRepository;
    private final UserRepository userRepository;
    private final MessageRepository messageRepository;

    public ChatService(final ChatRepository chatRepository, final UserRepository userRepository,
            final MessageRepository messageRepository) {
        this.chatRepository = chatRepository;
        this.userRepository = userRepository;
        this.messageRepository = messageRepository;
    }

    public List<ChatDTO> findAll() {
        final List<Chat> chats = chatRepository.findAll(Sort.by("chatId"));
        return chats.stream()
                .map(chat -> mapToDTO(chat, new ChatDTO()))
                .toList();
    }

    public ChatDTO get(final Integer chatId) {
        return chatRepository.findById(chatId)
                .map(chat -> mapToDTO(chat, new ChatDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Integer create(final ChatDTO chatDTO) {
        final Chat chat = new Chat();
        mapToEntity(chatDTO, chat);
        return chatRepository.save(chat).getChatId();
    }

    public void update(final Integer chatId, final ChatDTO chatDTO) {
        final Chat chat = chatRepository.findById(chatId)
                .orElseThrow(NotFoundException::new);
        mapToEntity(chatDTO, chat);
        chatRepository.save(chat);
    }

    public void delete(final Integer chatId) {
        chatRepository.deleteById(chatId);
    }

    private ChatDTO mapToDTO(final Chat chat, final ChatDTO chatDTO) {
        chatDTO.setChatId(chat.getChatId());
        chatDTO.setChatUserId1(chat.getChatUserId1() == null ? null : chat.getChatUserId1().getUserId());
        chatDTO.setChatUserId2(chat.getChatUserId2() == null ? null : chat.getChatUserId2().getUserId());
        return chatDTO;
    }

    private Chat mapToEntity(final ChatDTO chatDTO, final Chat chat) {
        final User chatUserId1 = chatDTO.getChatUserId1() == null ? null : userRepository.findById(chatDTO.getChatUserId1())
                .orElseThrow(() -> new NotFoundException("chatUserId1 not found"));
        chat.setChatUserId1(chatUserId1);
        final User chatUserId2 = chatDTO.getChatUserId2() == null ? null : userRepository.findById(chatDTO.getChatUserId2())
                .orElseThrow(() -> new NotFoundException("chatUserId2 not found"));
        chat.setChatUserId2(chatUserId2);
        return chat;
    }

    public ReferencedWarning getReferencedWarning(final Integer chatId) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final Chat chat = chatRepository.findById(chatId)
                .orElseThrow(NotFoundException::new);
        final Message chatMessage = messageRepository.findFirstByChat(chat);
        if (chatMessage != null) {
            referencedWarning.setKey("chat.message.chat.referenced");
            referencedWarning.addParam(chatMessage.getMessageId());
            return referencedWarning;
        }
        return null;
    }

}
