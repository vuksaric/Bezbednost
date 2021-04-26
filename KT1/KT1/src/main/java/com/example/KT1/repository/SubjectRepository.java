package com.example.KT1.repository;

import com.example.KT1.model.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubjectRepository extends JpaRepository<Subject, Long> {
    Subject findOneById(Long id);

    Subject findOneByEmail(String email);
}
