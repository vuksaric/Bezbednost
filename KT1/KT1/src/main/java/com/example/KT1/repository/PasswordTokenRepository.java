package com.example.KT1.repository;

import com.example.KT1.model.PasswordToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PasswordTokenRepository extends JpaRepository<PasswordToken, Long> {
    PasswordToken findOneByEmail(String email);
    PasswordToken findOneByToken(String email);
}
