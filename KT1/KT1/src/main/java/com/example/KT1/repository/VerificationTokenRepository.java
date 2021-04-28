package com.example.KT1.repository;

import com.example.KT1.model.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Long> {
    VerificationToken findOneByEmail(String email);
}
