package com.ictedu.dogether.userapi.entity;

import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Setter

@Entity
@Table(name = "User")
public class User {

    @Id // 프라이머리 키
    @Column(name = "user_id")
    private String userId; // 회원 아이디

    @Column(name = "user_name", nullable = false)
    private String userName; // 회원 이름

    @Column(name = "user_pass", nullable = false)
    private String userPass; // 비밀번호

    @Column(name = "user_phone", nullable = false)
    private String userPhone; // 회원 전화번호

    @Column(name = "user_email", nullable = false, unique = true)
    private String userEmail; // 회원 이메일

    @Column(name = "post_no", nullable = false)
    private int postNo; // 회원 우편번호

    @Column(name = "post_addr", nullable = false)
    private String postAddr; // 회원 주소

    private int score; // 회원 퀴즈 점수

    @Enumerated(EnumType.STRING)
    //@ColumnDefault("'COMMON'") // Enum타입으로 안쪽에 홑따옴표.
    @Builder.Default
    private Role role = Role.COMMON;

    // 카카오 로그인 시 발급받는 accessToken을 저장 -> 로그아웃 때 필요.
    private String accessToken;

}
