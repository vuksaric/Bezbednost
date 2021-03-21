package com.example.KT1.repository;

import com.example.KT1.model.Issuer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends JpaRepository<Issuer, Long> {
}
