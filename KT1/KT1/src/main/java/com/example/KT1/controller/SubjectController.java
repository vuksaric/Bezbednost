package com.example.KT1.controller;

import com.example.KT1.dto.CertificateDTO;
import com.example.KT1.dto.request.GetEmailRequest;
import com.example.KT1.dto.request.GetIdRequest;
import com.example.KT1.dto.response.SubjectResponse;
import com.example.KT1.model.Subject;
import com.example.KT1.services.implementation.OcspService;
import com.example.KT1.services.implementation.SubjectService;
import com.itextpdf.text.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    //admin odobrava
    @PutMapping("/approve")
    //@PreAuthorize("hasAuthority('APPROVE')")
    public void approveRegistrationRequest(@RequestBody GetIdRequest request){
        subjectService.approveRegistrationRequest(request);
    }

    @PutMapping("/deny")
    //@PreAuthorize("hasAuthority('DENY')")
    public void denyRegistrationRequest(@RequestBody GetIdRequest request){
        subjectService.denyRegistrationRequest(request);
    }

    //user potvrdjuje na mail-u
    @PutMapping("/confirm")
    public void confirmRegistrationRequest(@RequestBody GetEmailRequest request){
        subjectService.confirmRegistrationRequest(request);
    }

    @GetMapping("/registration-requests")
    //@PreAuthorize("hasAuthority('REGISTER')")
    public List<SubjectResponse> getRegistrationRequests(){
        return subjectService.getRegistrationRequests();
    }




}
