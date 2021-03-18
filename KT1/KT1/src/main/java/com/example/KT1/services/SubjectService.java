package com.example.KT1.services;

import com.example.KT1.certificate.CertificateGenerator;
import com.example.KT1.keyStore.KeyStoreWriter;
import com.example.KT1.model.Subject;
import com.example.KT1.repository.SubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.KeyPair;

@Service
public class SubjectService {

    @Autowired
    SubjectRepository subjectRepository;

    public Subject findOne(long idI) {
        return subjectRepository.findById(idI).orElseGet(null);
    }

    public void createCertificate(String type, String days, String subjectId, String issuerId){
        long idI = Long.parseLong(issuerId);
        long idS = Long.parseLong(subjectId);
        int numDays = Integer.parseInt(days);
        Subject subject = findOne((idS));
        if( type.equalsIgnoreCase("root")){
            Subject issuer = subject;
            subject.setCA(true);
        }
        else{
            Subject issuer = findOne(idI);
            if (type.equalsIgnoreCase("intermediate"))
                subject.setCA(true);
        }

        subject.setCertificate(true);
        subjectRepository.save(subject);
        KeyStoreWriter ks=new KeyStoreWriter();
        KeyPair keyPar = ks.generateKeyPair();
        char[] array = "tim17".toCharArray();
        CertificateGenerator certgen= new CertificateGenerator();
    }
}
