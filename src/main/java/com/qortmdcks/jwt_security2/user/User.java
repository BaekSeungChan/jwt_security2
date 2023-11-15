package com.qortmdcks.jwt_security2.user;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Data  // Lombok: 게터, 세터, toString, equals, hashCode 자동 생성
@Builder  // Lombok: 빌더 패턴 구현
@NoArgsConstructor  // Lombok: 파라미터 없는 기본 생성자 생성
@AllArgsConstructor  // Lombok: 모든 필드를 포함한 생성자 생성
@Entity  // JPA: 이 클래스를 데이터베이스 엔티티로 표시
@Table(name = "users")  // JPA: 'users' 테이블과 매핑
public class User implements UserDetails {
    @Id  // JPA: 이 필드를 기본 키(primary key)로 지정
    @GeneratedValue  // JPA: 기본 키 값 자동 생성 (예: auto increment)
    private Integer id;  // 사용자 ID
    private String firstname;  // 사용자 이름
    private String lastname;  // 사용자 성
    private String email;  // 사용자 이메일
    private String password;  // 사용자 비밀번호


    @Enumerated(EnumType.STRING)
    private Role role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }
    @Override
    public String getUsername() {
        return email;  // 사용자의 이름을 반환 (현재 구현되지 않음)
    }

    @Override
    public String getPassword(){
        return password;
    }


    @Override
    public boolean isAccountNonExpired() {
        return false;  // 계정이 만료되지 않았는지 여부 (현재 항상 false 반환)
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;  // 계정이 잠겨있지 않은지 여부 (현재 항상 false 반환)
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;  // 자격 증명(비밀번호)이 만료되지 않았는지 여부 (현재 항상 false 반환)
    }

    @Override
    public boolean isEnabled() {
        return false;  // 계정이 활성화 되어 있는지 여부 (현재 항상 false 반환)
    }
}
