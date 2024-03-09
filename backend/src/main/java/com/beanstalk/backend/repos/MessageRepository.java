package com.beanstalk.backend.repos;

import com.beanstalk.backend.domain.Chat;
import com.beanstalk.backend.domain.Message;
import com.beanstalk.backend.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface MessageRepository extends JpaRepository<Message, Integer> {

    Message findFirstByChat(Chat chat);

    Message findFirstByMessageSender(User user);

}
