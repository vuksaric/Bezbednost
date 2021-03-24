package com.example.KT1.services;

import com.example.KT1.certificate.CertificateGenerator;
import com.example.KT1.dto.CertificateDTO;
import com.example.KT1.keyStore.KeyStoreReader;
import com.example.KT1.keyStore.KeyStoreWriter;
import com.example.KT1.model.Subject;
import com.example.KT1.repository.SubjectRepository;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import org.bouncycastle.asn1.x500.RDN;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x500.style.BCStyle;
import org.bouncycastle.asn1.x500.style.IETFUtils;
import org.bouncycastle.cert.jcajce.JcaX509CertificateHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.security.*;
import java.security.cert.Certificate;
import java.security.cert.CertificateEncodingException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Date;
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

    public void downloadCert(Subject subject) throws FileNotFoundException, DocumentException, CertificateEncodingException {
        KeyStoreWriter kw=new KeyStoreWriter();
        char[] array = "tim17".toCharArray();
        X509Certificate cert;
        if(subject.isCA()){
            kw.loadKeyStore("interCertificate.jks",array);
            KeyStoreReader kr = new KeyStoreReader();
            cert = (X509Certificate) kr.readCertificate("interCertificate.jks", "tim17", subject.getId().toString());
        }else{
            kw.loadKeyStore("endEntity.jks",array);
            KeyStoreReader kr = new KeyStoreReader();
            cert = (X509Certificate) kr.readCertificate("endEntity.jks", "tim17", subject.getId().toString());
        }

        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream("Certificate" + subject.getId().toString() +".pdf"));


        X500Name x500Name = new JcaX509CertificateHolder(cert).getSubject();
        RDN cn = x500Name.getRDNs(BCStyle.CN)[0];
        String subject2 = IETFUtils.valueToString(cn.getFirst().getValue());
        Date startDate = cert.getNotBefore();
        Date endDate = cert.getNotAfter();
        String issuer = cert.getIssuerDN().getName();
        RDN e = x500Name.getRDNs(BCStyle.E)[0];
        String email = IETFUtils.valueToString(e.getFirst().getValue());
        RDN o = x500Name.getRDNs(BCStyle.O)[0];
        String organisation = IETFUtils.valueToString(o.getFirst().getValue());
        RDN ou = x500Name.getRDNs(BCStyle.OU)[0];
        String organisationUnit = IETFUtils.valueToString(ou.getFirst().getValue());
        String publicKey = cert.getPublicKey().toString();

        document.open();
        Font font = FontFactory.getFont(FontFactory.COURIER, 16, BaseColor.BLACK);
        Paragraph chunk = new Paragraph("Subject: " + subject2, font);
        Paragraph chunk1 = new Paragraph("Start date: " + startDate.toString(), font);
        Paragraph chunk2 = new Paragraph("End date: " + endDate.toString(), font);
        Paragraph chunk3 = new Paragraph("Issuer: " + issuer, font);
        Paragraph chunk4 = new Paragraph("Email: " + email, font);
        Paragraph chunk5 = new Paragraph("Organisation: " + organisation , font);
        Paragraph chunk6 = new Paragraph("Organisation unit: " + organisationUnit , font);
        Paragraph chunk7 = new Paragraph("Public key: " + publicKey , font);
        Paragraph chunk8 = new Paragraph("CERTIFICATE", font);

        document.add(chunk8);
        document.add(chunk);
        document.add(chunk1);
        document.add(chunk2);
        document.add(chunk3);
        document.add(chunk4);
        document.add(chunk5);
        document.add(chunk6);
        document.add(chunk7);
        document.close();

    }

    public List<Subject> getAll() {
        return subjectRepository.findAll();
    }


    public List<CertificateDTO> getCertChain(long idSubject) throws CertificateEncodingException, NoSuchAlgorithmException, InvalidKeyException, SignatureException {
        Subject subject = subjectRepository.findById(idSubject).orElseGet(null);
        List<CertificateDTO> certificateDTOList = new ArrayList<CertificateDTO>();
        KeyStoreWriter kw=new KeyStoreWriter();
        char[] array = "tim17".toCharArray();
        KeyStoreReader kr = new KeyStoreReader();
        Certificate cert;
        if(subject.isCA()){
            kw.loadKeyStore("interCertificate.jks",array);
            cert = kr.readCertificate("interCertificate.jks", "tim17", subject.getId().toString());
            System.out.println(cert);
        }
        else{
            kw.loadKeyStore("endEntity.jks",array);
            cert = kr.readCertificate("endEntity.jks", "tim17", subject.getId().toString());
            System.out.println(cert);
        }

        return getParents((X509Certificate) cert, certificateDTOList);

    }

    public List<CertificateDTO> getParents(X509Certificate certificate, List<CertificateDTO> certificateDTOList) throws CertificateEncodingException, NoSuchAlgorithmException, InvalidKeyException, SignatureException {
        X509Certificate parent = null;
        KeyStoreWriter kw=new KeyStoreWriter();
        char[] array = "tim17".toCharArray();

        X500Name x500name = new JcaX509CertificateHolder(certificate).getIssuer();
        RDN uid = x500name.getRDNs(BCStyle.UID)[0];
        RDN ou = x500name.getRDNs(BCStyle.OU)[0];
        String alias = IETFUtils.valueToString(uid.getFirst().getValue());
        String organisationUnit = IETFUtils.valueToString(ou.getFirst().getValue());
        System.out.println(alias);

        KeyStoreReader kr = new KeyStoreReader();
        kw.loadKeyStore("interCertificate.jks",array);
        parent = (X509Certificate) kr.readCertificate("interCertificate.jks", "tim17", alias);

        certificateDTOList.add(new CertificateDTO(certificate));
        System.out.println("SERTIFIKAT: " + certificate);

        if(organisationUnit.equalsIgnoreCase("admin")){
            kw.loadKeyStore("root.jks",array);
            parent = (X509Certificate) kr.readCertificate("root.jks", "tim17", alias);
            System.out.println("ROOT: " + parent);
            certificateDTOList.add(new CertificateDTO(parent));
            System.out.println(certificateDTOList.size());
            return certificateDTOList;
        }

        return getParents(parent, certificateDTOList);

    }

    public void delete(Subject subject) {
        subjectRepository.deleteById(subject.getId());
    }
}
