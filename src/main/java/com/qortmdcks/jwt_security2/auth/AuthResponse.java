package com.qortmdcks.jwt_security2.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data  // Lombok: 게터, 세터, toString, equals, hashCode 자동 생성
@Builder  // Lombok: 빌더 패턴 구현
@AllArgsConstructor  // Lombok: 모든 필드를 포함한 생성자 생성
@NoArgsConstructor  // Lombok: 파라미터 없는 기본 생성자 생성
public class AuthResponse {
    @JsonProperty("access_token")
    private String accessToken;  // 접근 토큰 필드

    @JsonProperty("refresh_token")
    private String refreshToken;  // 갱신 토큰 필드
}
// 인증 응답을 나타내는 클래스. 접근 토큰 및 갱신 토큰을 포함
