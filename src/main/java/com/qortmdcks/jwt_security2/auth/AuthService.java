package com.qortmdcks.jwt_security2.auth;

import com.qortmdcks.jwt_security2.config.JwtService;
import com.qortmdcks.jwt_security2.token.TokenRepository;
import com.qortmdcks.jwt_security2.user.User;
import com.qortmdcks.jwt_security2.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service  // Spring의 서비스 컴포넌트로 정의
@RequiredArgsConstructor  // Lombok: final 또는 @NonNull 필드에 대한 생성자 자동 생성
public class AuthService {
    private final UserRepository userRepository;  // UserRepository 주입
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public ResponseEntity<?> register(RegisterRequest request){

        var user = User.builder()
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())
                .build();

        userRepository.save(user);
        return ResponseEntity.ok(new MessageResponse("User register successfully"));

    }

}
// 인증 서비스 로직을 담당하는 클래스
