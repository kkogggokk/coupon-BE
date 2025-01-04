package com.example.couponcore.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "user_tb") // user_tb 테이블과 매핑
public class User { // v3.2

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 기본 키 자동 생성
    private Long id;

    @Column(nullable = false, unique = true, length = 100)
    private String email;

    @Column(nullable = false, length = 256)
    private String password;

    @Column(nullable = false, length = 45)
    private String username;

    @Column(nullable = true, length = 30)
    private String roles;
}
