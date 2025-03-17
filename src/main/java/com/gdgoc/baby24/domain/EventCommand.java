package com.gdgoc.baby24.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "event_commands")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EventCommand {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // FK: Device
    @ManyToOne
    @JoinColumn(name = "device_id")
    private Device device;

    // FK: User
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    // 이벤트 이름
    @Column
    private String event;

    // 실행할 명령
    @Column
    private String commandAction;

    // 명령 등록 일시
    @Column
    private LocalDateTime createdAt;
}
