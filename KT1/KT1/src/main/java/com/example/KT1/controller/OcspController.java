package com.example.KT1.controller;

import com.example.KT1.dto.IdDTO;
import com.example.KT1.keyStore.KeyStoreReader;
import com.example.KT1.keyStore.KeyStoreWriter;
import com.example.KT1.model.Subject;
import com.example.KT1.services.implementation.OcspService;
import com.example.KT1.services.implementation.SubjectService;
import org.bouncycastle.operator.OperatorCreationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.text.ParseException;

@RestController

@RequestMapping(value = "/ocsp")
public class OcspController {

    @Autowired
    OcspService ocspService;

    @Autowired
    SubjectService subjectService;

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping(value="/checkValidity/{id}")
    public boolean checkWithdrawal(@PathVariable String id) throws CertificateEncodingException, NoSuchAlgorithmException, InvalidKeyException, SignatureException {
        long num = Long.parseLong(id);
        Subject subject = subjectService.findOne((num));
        KeyStoreWriter kw=new KeyStoreWriter();
        char[] array = "tim17".toCharArray();
        KeyStoreReader kr = new KeyStoreReader();
        if(subject.isCA()==true){
            kw.loadKeyStore("interCertificate.jks",array);
            X509Certificate cert = (X509Certificate) kr.readCertificate("interCertificate.jks", "tim17", id);
            Boolean validan = ocspService.checkParents(cert);
            return validan;
        }else{
            kw.loadKeyStore("endEntity.jks",array);
            X509Certificate cert = (X509Certificate) kr.readCertificate("endEntity.jks", "tim17", id);
            Boolean validan = ocspService.checkParents(cert);
            return validan;
        }
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping(value="/revoke")
    public void revokeCert(@RequestBody IdDTO idDto) throws CertificateException, OperatorCreationException, IOException, ParseException {

        long num = Long.parseLong(idDto.getId());
        Subject subject = subjectService.findOne((num));
        KeyStoreWriter kw = new KeyStoreWriter();
        char[] array = "tim17".toCharArray();
        KeyStoreReader kr = new KeyStoreReader();

        if(subject.isCA()==true){
            kw.loadKeyStore("interCertificate.jks",array);
            X509Certificate cert = (X509Certificate) kr.readCertificate("interCertificate.jks", "tim17", idDto.getId());
            ocspService.revokeCertificate(cert);
        }else{
            kw.loadKeyStore("endEntity.jks",array);
            X509Certificate cert = (X509Certificate) kr.readCertificate("endEntity.jks", "tim17", idDto.getId());
            ocspService.revokeCertificate(cert);
        }
    };


}
