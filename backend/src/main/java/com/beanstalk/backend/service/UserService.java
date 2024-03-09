package com.beanstalk.backend.service;

import com.beanstalk.backend.domain.Chat;
import com.beanstalk.backend.domain.Message;
import com.beanstalk.backend.domain.User;
import com.beanstalk.backend.model.UserDTO;
import com.beanstalk.backend.repos.ChatRepository;
import com.beanstalk.backend.repos.MessageRepository;
import com.beanstalk.backend.repos.UserRepository;
import com.beanstalk.backend.util.NotFoundException;
import com.beanstalk.backend.util.ReferencedWarning;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class UserService {

    private final UserRepository userRepository;
    private final ChatRepository chatRepository;
    private final MessageRepository messageRepository;

    public UserService(final UserRepository userRepository, final ChatRepository chatRepository,
            final MessageRepository messageRepository) {
        this.userRepository = userRepository;
        this.chatRepository = chatRepository;
        this.messageRepository = messageRepository;
    }

    public List<UserDTO> findAll() {
        final List<User> users = userRepository.findAll(Sort.by("userId"));
        return users.stream()
                .map(user -> mapToDTO(user, new UserDTO()))
                .toList();
    }

    public UserDTO get(final Integer userId) {
        return userRepository.findById(userId)
                .map(user -> mapToDTO(user, new UserDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Integer create(final UserDTO userDTO) {
        final User user = new User();
        mapToEntity(userDTO, user);
        return userRepository.save(user).getUserId();
    }

    public void update(final Integer userId, final UserDTO userDTO) {
        final User user = userRepository.findById(userId)
                .orElseThrow(NotFoundException::new);
        mapToEntity(userDTO, user);
        userRepository.save(user);
    }

    public void delete(final Integer userId) {
        userRepository.deleteById(userId);
    }

    private UserDTO mapToDTO(final User user, final UserDTO userDTO) {
        userDTO.setUserId(user.getUserId());
        userDTO.setUserName(user.getUserName());
        userDTO.setOauthUserName(user.getOauthUserName());
        userDTO.setAuth0Id(user.getAuth0Id());
        return userDTO;
    }

    private User mapToEntity(final UserDTO userDTO, final User user) {
        user.setUserName(userDTO.getUserName());
        user.setOauthUserName(userDTO.getOauthUserName());
        user.setAuth0Id(userDTO.getAuth0Id());
        return user;
    }

    public boolean userNameExists(final String userName) {
        return userRepository.existsByUserNameIgnoreCase(userName);
    }

    public ReferencedWarning getReferencedWarning(final Integer userId) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final User user = userRepository.findById(userId)
                .orElseThrow(NotFoundException::new);
        final Chat chatUserId1Chat = chatRepository.findFirstByChatUserId1(user);
        if (chatUserId1Chat != null) {
            referencedWarning.setKey("user.chat.chatUserId1.referenced");
            referencedWarning.addParam(chatUserId1Chat.getChatId());
            return referencedWarning;
        }
        final Chat chatUserId2Chat = chatRepository.findFirstByChatUserId2(user);
        if (chatUserId2Chat != null) {
            referencedWarning.setKey("user.chat.chatUserId2.referenced");
            referencedWarning.addParam(chatUserId2Chat.getChatId());
            return referencedWarning;
        }
        final Message messageSenderMessage = messageRepository.findFirstByMessageSender(user);
        if (messageSenderMessage != null) {
            referencedWarning.setKey("user.message.messageSender.referenced");
            referencedWarning.addParam(messageSenderMessage.getMessageId());
            return referencedWarning;
        }
        return null;
    }

}
