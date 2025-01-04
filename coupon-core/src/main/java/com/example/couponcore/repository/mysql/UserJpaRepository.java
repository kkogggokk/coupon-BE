package com.example.couponcore.repository.mysql;

import com.example.couponcore.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserJpaRepository extends JpaRepository<User, Long> {  // v3.2
}
