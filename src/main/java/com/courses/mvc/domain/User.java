package com.courses.mvc.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.util.List;

/**
 * @author Stepan
 */
@Getter
@Setter
@Entity(name = "chat_users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(unique = true)
    private String login;

    private String password;

    @ManyToOne(cascade = CascadeType.ALL)
    private UserRole role;

    @OneToMany(fetch = FetchType.LAZY, orphanRemoval = true, cascade = CascadeType.ALL)
    @JoinTable(name = "messages", inverseJoinColumns = @JoinColumn(name = "receiver_id"))
    private List<Message> receivedMessages;

    @OneToMany(fetch = FetchType.LAZY, orphanRemoval = true,cascade = CascadeType.ALL)
    @JoinTable(name = "messages", inverseJoinColumns = @JoinColumn(name = "sender_id"))
    private List<Message> sentMessages;

    @OneToOne(cascade = CascadeType.ALL)
    BlackList blackList;
}
