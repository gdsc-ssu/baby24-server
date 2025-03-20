package com.gdgoc.baby24.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "streams")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Stream {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // FK: Room
    @ManyToOne
    @JoinColumn(name = "room_id")
    private Room room;

    // 스트리밍 URL
    @Column
    private String url;

    // 스트리밍 상태
    @Column
    private String status;

    // 시작 시간
    @Column
    private LocalDateTime startedAt;

    // 종료 시간
    @Column
    private LocalDateTime endedAt;


    @OneToMany(mappedBy = "stream", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Analysis> analyses = new ArrayList<>();
}
