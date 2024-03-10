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
    @SequenceGenerator(
            name = "primary_sequence",
            sequenceName = "primary_sequence",
            allocationSize = 1,
            initialValue = 10000
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "primary_sequence"
    )
    private Integer userId;

    @Column(nullable = false, unique = true, length = 50, name = "username")
    private String userName;

    @Column(nullable = false, length = 50, name = "oauthusername")
    private String oauthUserName;

    @Column(nullable = false, length = 50, name = "auth0id")
    private String auth0Id;

    @OneToMany(mappedBy = "chatUserId1")
    private Set<Chat> chatUserId1Chats;

    @OneToMany(mappedBy = "chatUserId2")
    private Set<Chat> chatUserId2Chats;

    @OneToMany(mappedBy = "messageSender")
    private Set<Message> messageSenderMessages;

}
