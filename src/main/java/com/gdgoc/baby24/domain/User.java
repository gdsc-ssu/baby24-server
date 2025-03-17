package com.gdgoc.baby24.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 사용자 이름
    @Column
    private String username;

    // 이메일
    @Column
    private String email;

    // 구글 아이디
    @Column
    private Integer googleId;

    // 생성 일시
    @Column
    private LocalDateTime createdAt;

    // PAT (예: 토큰)
    @Column
    private String PAT;

    // 비상연락망
    @Column
    private Integer emergencyContacts;


    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Device> devices = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Room> rooms = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<EventCommand> eventCommands = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Analysis> analyses = new ArrayList<>();
}
