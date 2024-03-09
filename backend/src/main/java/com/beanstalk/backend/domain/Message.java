package com.beanstalk.backend.domain;

import jakarta.persistence.Table;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import java.time.OffsetDateTime;
import lombok.Getter;
import lombok.Setter;


@Entity
@Table(name = "messages")
@Getter
@Setter
public class Message {

    @Id
    @Column(nullable = false, updatable = false, name = "messageid")
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
    private Integer messageId;

    @Column(nullable = false, length = 20, name = "messagecontent")
    private String messageContent;

    @Column(nullable = false, columnDefinition = "datetime2", name = "messagetimestamp")
    private OffsetDateTime messageTimeStamp;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chatid", nullable = false)
    private Chat chat;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "messagesenderid", nullable = false)
    private User messageSender;

}
