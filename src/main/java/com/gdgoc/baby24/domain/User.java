package com.gdgoc.baby24.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    String username;
    String email;
    String PAT;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Device> reviews = new ArrayList<>();
}
