package com.qortmdcks.jwt_security2.auth;


import com.qortmdcks.jwt_security2.user.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data  // Lombok: 게터, 세터, toString, equals, hashCode 자동 생성
@Builder  // Lombok: 빌더 패턴 구현
@AllArgsConstructor  // Lombok: 모든 필드를 포함한 생성자 생성
@NoArgsConstructor  // Lombok: 파라미터 없는 기본 생성자 생성
public class RegisterRequest {
    private String firstname;  // 사용자 이름 필드
    private String lastname;  // 사용자 성 필드
    private String email;  // 사용자 이메일 필드
    private String password;  // 사용자 비밀번호 필드
    private Role role;  // 사용자 역할 필드
}
// 회원가입 요청 시 사용되는 데이터 모델
