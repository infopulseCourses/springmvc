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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.util.List;

/**
 * Created by vedmant on 2/11/17.
 */
@Entity(name = "users")
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(unique = true)
    private String login;

    private String password;

    @ManyToOne(fetch = FetchType.EAGER)
    private UserRole role;

    @OneToMany(mappedBy = "sender",fetch = FetchType.LAZY, orphanRemoval = true, cascade = CascadeType.ALL)
    private List<Message> sentMessages;

    @OneToMany(mappedBy = "receiver",fetch = FetchType.LAZY, orphanRemoval = true, cascade = CascadeType.ALL)
    private List<Message> receivedMessages;

    @OneToOne(mappedBy = "user",cascade = CascadeType.ALL)
    private BlackList blackList;
}
