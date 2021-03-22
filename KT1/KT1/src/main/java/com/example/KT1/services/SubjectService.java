package com.example.KT1.services;

import com.example.KT1.certificate.CertificateGenerator;
import com.example.KT1.keyStore.KeyStoreWriter;
import com.example.KT1.model.Subject;
import com.example.KT1.repository.SubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.KeyPair;
import java.util.List;

@Service
public class SubjectService {

    @Autowired
    SubjectRepository subjectRepository;


    public Subject findOne(long idI) {
        return subjectRepository.findById(idI).orElseGet(null);
    }


    public void save(Subject subject) { subjectRepository.save(subject); }

    public List<Subject> findAll() { return subjectRepository.findAll(); }
}
