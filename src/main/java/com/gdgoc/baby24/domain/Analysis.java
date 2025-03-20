package com.gdgoc.baby24.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "analysis")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Analysis {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // FK: Stream
    @ManyToOne
    @JoinColumn(name = "stream_id")
    private Stream stream;

    // FK: User (ERD 상에 사용자 식별자가 있으면)
    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users user;

    // 분석 결과
    @Column
    private String result;

    // 분석 완료 시각
    @Column
    private LocalDateTime timestamp;
}
