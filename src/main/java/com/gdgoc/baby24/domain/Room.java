package com.gdgoc.baby24.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "rooms")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // FK: User
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    // 방 이름
    @Column
    private String roomName;

    // 생성 일시
    @Column
    private LocalDateTime createdAt;


    // @Column
    // private Integer id3;
    // @Column
    // private Integer id22;

    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Stream> streams = new ArrayList<>();
}
