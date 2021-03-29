package com.example.KT1.services;

import com.example.KT1.certificate.CertificateGenerator;
import com.example.KT1.dto.CertificateDTO;
import com.example.KT1.dto.ExtensionDTO;
import com.example.KT1.keyStore.KeyStoreReader;
import com.example.KT1.keyStore.KeyStoreWriter;
import com.example.KT1.model.Issuer;
import com.example.KT1.model.OCSP;
import com.example.KT1.model.Subject;
import com.example.KT1.repository.AdminRepository;
import org.bouncycastle.cert.CertIOException;
import org.bouncycastle.operator.OperatorCreationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.cert.Certificate;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;

@Service
public class AdminService {

    @Autowired
    AdminRepository adminRepository;

    @Autowired
    SubjectService subjectService;

    @Autowired
    OcspService ocspService;

    public Issuer findOne(long idI) {
        return adminRepository.findById(idI).orElseGet(null);
    }

    public void createCertificate(String type, String days, String subjectId, String issuerId, ExtensionDTO extensionDTO) throws OperatorCreationException, CertIOException, CertificateException {
        System.out.println("TYPE: " + type);
        long idI = Long.parseLong(issuerId);
        long idS = Long.parseLong(subjectId);
        System.out.println(idI);
        System.out.println(idS);
        int numDays = Integer.parseInt(days);
        Issuer issuer = null;
        Subject subject;
        Subject issuer2 = null;
        if( type.equalsIgnoreCase("root")){
            issuer = findOne(idI);
            subject = new Subject(issuer);
            //System.out.println(issuer.getId());
            //System.out.println(subject.getId());
            System.out.println("USAO U ROOT, CA JE" + " " + subject.getName());
        }
        else if( type.equalsIgnoreCase("interRoot")){
            issuer = findOne(idI);
            subject = subjectService.findOne(idS);
            subject.setCA(true);
            System.out.println("USAO U INTERROOT, CA JE" + " " + subject.getName());
        }
        else{
            System.out.println("USAO U END");
            issuer2 = subjectService.findOne(idI);
            subject = subjectService.findOne(idS);
            if (!type.equalsIgnoreCase("endEntity")) {
                System.out.println("USAO U INTER, CA JE");
                subject.setCA(true);
            }
        }

        subject.setCertificate(true);
        /*if(!type.equalsIgnoreCase("root"))
            subjectService.delete(subject);*/
        subjectService.save(subject);
        KeyStoreWriter kw=new KeyStoreWriter();
        KeyPair keyPair = kw.generateKeyPair();
        char[] array = "tim17".toCharArray();
        CertificateGenerator certgen= new CertificateGenerator();
        Certificate certificate;
        if( type.equalsIgnoreCase("root")){
            certificate = certgen.generateCertRoot(subject,issuer,keyPair,"SHA256WithRSAEncryption",numDays,extensionDTO);
            System.out.println(certificate);
        }
        else if(type.equalsIgnoreCase("interRoot")){
            certificate = certgen.generateCertFromRoot(subject,issuer,keyPair,"SHA256WithRSAEncryption",numDays,extensionDTO);
        }
        else{
            certificate = certgen.generateCertFromInter(subject,issuer2,keyPair,"SHA256WithRSAEncryption",numDays,extensionDTO);
        }

        KeyStoreReader kr = new KeyStoreReader();
        X509Certificate cert;
        if(type.equalsIgnoreCase("root")){
            System.out.println("SUBJECT: " + subject.getId() + " " + subject.getName());
            System.out.println("ISSUER: " + issuer.getId() + " " + issuer.getName());
            kw.loadKeyStore("root.jks",array);
            //System.out.println(keyPair.getPrivate().toString() + " " + subject.getId().toString() );
            // alijas, pk, password, cert
            kw.write(issuer.getId().toString(), keyPair.getPrivate() ,  issuer.getId().toString().toCharArray(), certificate);
            kw.saveKeyStore("root.jks", array);
            cert = (X509Certificate) kr.readCertificate("root.jks", "tim17", issuer.getId().toString());
            PrivateKey pk = kr.readPrivateKey("root.jks", "tim17", issuer.getId().toString(), issuer.getId().toString());
            //System.out.println(cert);
            System.out.println("PRIVATAN KLJUC: " + pk + " " + keyPair.getPrivate().toString());
        }
        else if(type.equalsIgnoreCase("endEntity")){
            kw.loadKeyStore("endEntity.jks",array);
            kw.write(subject.getId().toString(), keyPair.getPrivate() ,  subject.getId().toString().toCharArray(), certificate);
            kw.saveKeyStore("endEntity.jks", array);
            cert = (X509Certificate) kr.readCertificate("endEntity.jks", "tim17", subject.getId().toString());
            //System.out.println(cert);
        }
        else{
            kw.loadKeyStore("interCertificate.jks",array);
            kw.write(subject.getId().toString(), keyPair.getPrivate() ,  subject.getId().toString().toCharArray(), certificate);
            kw.saveKeyStore("interCertificate.jks", array);
            cert = (X509Certificate) kr.readCertificate("interCertificate.jks", "tim17", subject.getId().toString());
            //System.out.println(cert);
        }

        if(!type.equalsIgnoreCase("root")){
            OCSP ocsp = new OCSP();
            ocsp.setSerialNumber(cert.getSerialNumber());
            ocsp.setWithdrawn(false);
            ocspService.save(ocsp);
        }

    }

    public List<CertificateDTO> getAllCertificates() throws CertificateEncodingException {
        List<Issuer> issuerList = adminRepository.findAll();
        List<CertificateDTO> certificateDTOS = new ArrayList<CertificateDTO>();
        KeyStoreReader kr = new KeyStoreReader();
        KeyStoreWriter kw=new KeyStoreWriter();
        char[] array = "tim17".toCharArray();
        Certificate cert;
        for(Issuer issuer: issuerList){
            kw.loadKeyStore("root.jks",array);
            certificateDTOS.add(new CertificateDTO((X509Certificate) kr.readCertificate("root.jks", "tim17", issuer.getId().toString())));
        }

        List<Subject> subjectList = subjectService.findAll();
        for(Subject subject: subjectList){
            if(subject.isCertificate()) {
                if (subject.isCA() && !subject.getOrganisationUnit().equalsIgnoreCase("admin")) {
                    kw.loadKeyStore("interCertificate.jks", array);
                    certificateDTOS.add(new CertificateDTO((X509Certificate) kr.readCertificate("interCertificate.jks", "tim17", subject.getId().toString())));
                } else if (!subject.isCA()) {
                    kw.loadKeyStore("endEntity.jks", array);
                    certificateDTOS.add(new CertificateDTO((X509Certificate) kr.readCertificate("endEntity.jks", "tim17", subject.getId().toString())));
                }
            }
        }
        return certificateDTOS;
    }

    public List<Issuer> getAll() {
        return adminRepository.findAll();
    }
}
