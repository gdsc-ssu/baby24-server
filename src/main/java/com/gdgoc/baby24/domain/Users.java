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
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 사용자 이름
    @Column
    private String username;

    public String getName() {
        return username;
    }
    public void setName(String name) {
        this.username = name;
    }

    // 이메일
    @Column
    private String email;

    // 구글 아이디
    @Column
    private Integer googleId;

    @Column(nullable = false)
    private String password;

    @Column
    private String personalAccessToken; // PAT 필드 이름 변경 (main 브랜치 반영)



    // 비상연락망
    @Column
    private String emergencyContact;

    public void setEmergencyContact(String emergencyContact) {
        this.emergencyContact = emergencyContact;
    }



    @Column
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Device> reviews = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Room> rooms = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<EventCommand> eventCommands = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Analysis> analyses = new ArrayList<>();
}
