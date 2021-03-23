package com.example.KT1.repository;

import com.example.KT1.model.OCSP;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.List;

@Repository
public interface OcspRepository extends JpaRepository<OCSP, Long> {
    OCSP findOneById(long id);

    //List<OCSP> findAllByAdminId(long id);

    OCSP findOneBySerialNumber(BigInteger serialNumber);
}
