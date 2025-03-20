package com.gdgoc.baby24.domain;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "devices")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Device {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // FK: User
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    // 기기 이름
    @Column
    private String Name;

    // 기기 종류
    @Column
    private String Type;

    // 기기 유일번호
    @Column
    private String identifier;

    // 기기 상태
    @Column
    private String status;

    // 기기 등록 일시
    @Column
    private LocalDateTime registeredAt;
  
    @Column
    private Boolean alert;
  
    @Setter
      @ManyToOne(fetch = FetchType.LAZY)
      @JoinColumn(name = "user_id")
      private User user;
  
      public void setAlert(Boolean alert) {
          this.alert = alert;
      }


    @OneToMany(mappedBy = "device", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<EventCommand> eventCommands = new ArrayList<>();

}
