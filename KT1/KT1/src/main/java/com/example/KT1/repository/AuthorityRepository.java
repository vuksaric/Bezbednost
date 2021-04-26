package com.example.KT1.repository;

import com.example.KT1.model.Authority;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorityRepository extends JpaRepository<Authority, Long> {
    Authority findOneByName(String role_customer);

}

