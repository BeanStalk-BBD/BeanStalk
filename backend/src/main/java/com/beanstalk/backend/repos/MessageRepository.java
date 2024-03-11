package com.beanstalk.backend.repos;

import com.beanstalk.backend.domain.Chat;
import com.beanstalk.backend.domain.Message;
import com.beanstalk.backend.domain.User;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;


public interface MessageRepository extends JpaRepository<Message, Long> {

    Message findFirstByChat(Chat chat);

    Message findFirstByMessageSender(User user);

    List<Message> findAllByChat(Chat chat);

    @Query("SELECT m FROM Message m WHERE m.chat = ?1 ORDER BY m.messageTimeStamp DESC")
    List<Message> findTop10ByChat(Chat chat, Pageable pageable); 

    @Procedure("GetOrCreateChatSP")
    int getChatID(int UserID1, int UserID2);

    @Query(value = "SELECT UserName FROM GetOpenChats(:userID) ORDER BY LatestMessageTimeStamp DESC", nativeQuery = true)
    List<String> findOpenChatsByUserID(@Param("userID") int userID);    

}
