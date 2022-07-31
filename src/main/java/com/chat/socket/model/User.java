package com.chat.socket.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "USERS")
public class User {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    private String username;
//    private String nickname;
//    private String password;
//    private String profileImage;
//    private String proflieBgimage;
//    private String userStatus;
//    private String encodeUsername;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "user")
    private List<User> friends = new ArrayList<>();

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<ChatRoom> chatRoomList = new ArrayList<>();
}
