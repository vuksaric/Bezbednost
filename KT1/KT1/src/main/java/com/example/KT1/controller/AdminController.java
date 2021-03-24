package com.example.KT1.controller;

import com.example.KT1.dto.CertificateDTO;
import com.example.KT1.dto.ExtensionDTO;
import com.example.KT1.model.Issuer;
import com.example.KT1.model.Subject;
import com.example.KT1.services.AdminService;
import com.example.KT1.services.SubjectService;
import org.bouncycastle.operator.OperatorCreationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.util.List;


@RestController

@RequestMapping(value = "/admin")
public class AdminController {

    @Autowired
    AdminService adminServices;

    @Autowired
    SubjectService subjectService;

    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping(value="/addCertificate/{type}/{days}/{subjectId}/{issuerId}",consumes = MediaType.APPLICATION_JSON_VALUE)
    public void createCertificate(@PathVariable String type, @PathVariable String days, @PathVariable String subjectId, @PathVariable String issuerId, @RequestBody ExtensionDTO extensionDTO) throws CertificateException, OperatorCreationException, IOException {

        System.out.println(subjectId);
        System.out.println(issuerId);
        adminServices.createCertificate(type, days, subjectId, issuerId, extensionDTO);
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping(value = "/allCertificates")
    public List<CertificateDTO> getAllCertificates() throws CertificateEncodingException {
        return adminServices.getAllCertificates();
    }

    @GetMapping(value = "/getAllIssuer")
    public List<Issuer> getAllIssuers(){
        return adminServices.getAll();
    }
}
