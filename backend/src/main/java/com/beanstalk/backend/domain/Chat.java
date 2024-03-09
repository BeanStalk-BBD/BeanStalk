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
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;

@Table(name = "chat")
@Entity
@Getter
@Setter
public class Chat {

    @Id
    @Column(nullable = false, updatable = false, name = "chatid")
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
    private Integer chatId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chatuserid1", nullable = false)
    private User chatUserId1;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chatuserid2", nullable = false)
    private User chatUserId2;

    @OneToMany(mappedBy = "chat")
    private Set<Message> chatMessages;

}
