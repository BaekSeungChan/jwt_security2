package com.qortmdcks.jwt_security2.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service  // Spring의 서비스 컴포넌트로 정의
public class JwtService {

    @Value("${application.security.jwt.secret-key}")  // 프로퍼티 파일에서 JWT 시크릿 키를 주입
    private String secretKey;
    @Value("${application.security.jwt.expiration}")  // JWT 만료 시간(밀리초 단위)을 프로퍼티 파일에서 주입
    private long jwtExpiration;
    @Value("${application.security.jwt.refresh-token.expiration}")
    private long refreshExpiration;

    // JWT 토큰에서 사용자 이름(Subject) 추출
    public String extractUsername(String token){
        return extractClaim(token, Claims::getSubject);
    }

    // JWT 토큰에서 특정 클레임 추출
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver){
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    // 주어진 UserDetails를 사용하여 JWT 토큰 생성
    public String generateToken(UserDetails userDetails){
        return generateToken(new HashMap<>(), userDetails);
    }

    // 추가 클레임을 포함하여 JWT 토큰 생성
    public String generateToken(
            Map<String, Object> extractClaims,
            UserDetails userDetails
    ){
        return buildToken(new HashMap<>(), userDetails, refreshExpiration);
    }

    // JWT 토큰을 실제로 생성하는 내부 메소드
    private String buildToken(
            Map<String, Object> extraClaims,
            UserDetails userDetails,
            long expiration
    ){
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    // JWT 토큰의 유효성 검사 (사용자 정보 일치 여부 확인)
    public boolean isTokenValid(String token, UserDetails userDetails){
        final String userName = extractUsername(token);
        return (userName.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    // JWT 토큰의 만료 여부 확인
    private boolean isTokenExpired(String token){
        return extractExpiration(token).before(new Date());
    }

    // JWT 토큰에서 만료 시간 추출
    private Date extractExpiration(String token){
        return extractClaim(token, Claims::getExpiration);
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
