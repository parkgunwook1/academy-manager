package com.aihelper.customer_bot.security.repository;

import com.aihelper.customer_bot.security.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

// CRUD 함수를 JpaRepository가 들고있음.
// @Repository라는 어노테이션이 없어도 IOC된다. 이유는 JpaRepository를 상속했기 때문에 가능하다.
public interface UserRepository  extends JpaRepository<User , Integer> {
    // findBy규칙 -> Username 문법
    // select * from user where username = ?
    public User findByUsername(String username);

    public User findByEmail(String email);  // jpa query methods
}
