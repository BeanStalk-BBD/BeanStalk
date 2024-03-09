package com.beanstalk.backend.repos;

import com.beanstalk.backend.domain.Chat;
import com.beanstalk.backend.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ChatRepository extends JpaRepository<Chat, Integer> {

    Chat findFirstByChatUserId1(User user);

    Chat findFirstByChatUserId2(User user);

}
