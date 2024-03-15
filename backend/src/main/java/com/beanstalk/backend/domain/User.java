package com.beanstalk.backend.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;


@Entity
@Table(name = "Users")
@Getter
@Setter
public class User {

    @Id
    @Column(nullable = false, updatable = false, name = "userid")
   
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Integer userId;

    @Column(nullable = false, unique = true, length = 50, name = "username")
    private String userName;


    @OneToMany(mappedBy = "chatUserId1")
    private Set<Chat> chatUserId1Chats;

    @OneToMany(mappedBy = "chatUserId2")
    private Set<Chat> chatUserId2Chats;

    @OneToMany(mappedBy = "messageSender")
    private Set<Message> messageSenderMessages;

}
