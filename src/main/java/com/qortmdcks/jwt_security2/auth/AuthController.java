package com.qortmdcks.jwt_security2.auth;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController  // REST 컨트롤러로 정의, HTTP 요청을 처리하는 데 사용
@RequestMapping("/api/v1/auth")  // 이 컨트롤러의 모든 메소드에 적용되는 URL 경로 접두사
@RequiredArgsConstructor  // Lombok: final 또는 @NonNull 필드에 대한 생성자 자동 생성
public class AuthController {
    private final AuthService authService;  // AuthService 주입

    @PostMapping("/signup")
    public ResponseEntity<?> signupUser(@Valid @RequestBody RegisterRequest request){
        return ResponseEntity.ok(authService.register(request));
    }
    // 회원가입 요청을 처리하는 메소드. 유효한 RegisterRequest 객체를 받아 회원가입 로직 수행

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> loginUser(@Valid @RequestBody LoginRequest request){
        return ResponseEntity.ok(authService.login(request));
    }
    // 로그인 요청을 처리하는 메소드. 유효한 LoginRequest 객체를 받아 로그인 로직 수행
}

