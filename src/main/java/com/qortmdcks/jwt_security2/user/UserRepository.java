package com.qortmdcks.jwt_security2.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

// UserRepository 인터페이스는 JpaRepository를 상속받아, User 엔티티에 대한 기본적인 CRUD(Create, Read, Update, Delete)
// 및 페이징 처리 기능을 자동으로 제공합니다.
public interface UserRepository extends JpaRepository<User, Integer> {

    // findByEmail 메소드를 정의함으로써, 이메일을 기반으로 한 사용자 검색 기능을 추가합니다.
    // 이 메소드는 주어진 이메일 주소를 가진 사용자를 데이터베이스에서 찾아 Optional<User> 타입으로 반환합니다.
    // Optional은 해당 이메일 주소를 가진 사용자가 존재하지 않을 경우 null 대신 사용되어 NullPointerException을 방지합니다.
    Optional<User> findByEmail(String email);
}
