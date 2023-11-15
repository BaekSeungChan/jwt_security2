package com.qortmdcks.jwt_security2.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;

import java.security.Key;
import java.util.function.Function;

@Service  // Spring의 서비스 컴포넌트로 정의
public class JwtService {

    @Value("${application.security.jwt.secret-key}")  // 프로퍼티 파일에서 JWT 시크릿 키를 주입
    private String secretKey;
    @Value("${application.security.jwt.expiration}")  // JWT 만료 시간(밀리초 단위)을 프로퍼티 파일에서 주입
    private long jwtExpiration;

    // JWT 토큰에서 사용자 이름(Subject) 추출
    public String extractUsername(String token){
        return extractClaim(token, Claims::getSubject);
    }

    // JWT 토큰에서 특정 클레임 추출
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver){
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    // JWT 토큰에서 모든 클레임 추출
    public Claims extractAllClaims(String token){
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    // 시크릿 키를 사용하여 서명 키 생성
    private Key getSignInKey(){
        byte[] keyBytes = Decoders.BASE64URL.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
