package com.example.KT1.controller;

import com.example.KT1.dto.CertificateDTO;
import com.example.KT1.model.Issuer;
import com.example.KT1.model.Subject;
import com.example.KT1.services.OcspService;
import com.example.KT1.services.SubjectService;
import com.itextpdf.text.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileNotFoundException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.security.cert.CertificateEncodingException;
import java.util.List;


@RestController
@RequestMapping(value = "/subject")
public class SubjectController {

    @Autowired
    SubjectService subjectService;

    @Autowired
    OcspService ocspService;

    @GetMapping(value = "/download/{id}")
    public void downloadCert(@PathVariable String id) throws FileNotFoundException, DocumentException, CertificateEncodingException {

        long idSubject = Long.parseLong(id);

        Subject subject = subjectService.findOne((idSubject));
        subjectService.downloadCert(subject);

        //return new ResponseEntity<>(subjectDTO, HttpStatus.OK);
    }

    @GetMapping(value = "/getAllSubjects")
    public List<Subject> getAllSubjects(){
        return subjectService.getAll();
    }

    @GetMapping(value = "/getCertChain/{id}")
    public List<CertificateDTO> getSubject(@PathVariable String id) throws CertificateEncodingException, NoSuchAlgorithmException, InvalidKeyException, SignatureException {

        long idSubject = Long.parseLong(id);

        return subjectService.getCertChain(idSubject);
    }




}
