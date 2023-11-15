package com.qortmdcks.jwt_security2.auth;

import lombok.*;

@Data  // Lombok: 게터, 세터, toString, equals, hashCode 자동 생성
@Builder  // Lombok: 빌더 패턴 구현
@AllArgsConstructor  // Lombok: 모든 필드를 포함한 생성자 생성
@NoArgsConstructor  // Lombok: 파라미터 없는 기본 생성자 생성
public class LoginRequest {
    private String email;  // 사용자 이메일 필드
    String password;  // 사용자 비밀번호 필드
}
// 로그인 요청 시 사용되는 데이터 모델
