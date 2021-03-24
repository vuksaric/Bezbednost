package com.example.KT1.services;

import com.example.KT1.keyStore.KeyStoreReader;
import com.example.KT1.keyStore.KeyStoreWriter;
import com.example.KT1.model.OCSP;
import com.example.KT1.repository.OcspRepository;
import org.bouncycastle.asn1.x500.RDN;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x500.style.BCStyle;
import org.bouncycastle.asn1.x500.style.IETFUtils;
import org.bouncycastle.cert.jcajce.JcaX509CertificateHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.*;
import java.security.cert.CertificateEncodingException;
import java.security.cert.X509Certificate;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;

@Service
public class OcspService {

    @Autowired
    private OcspRepository ocspRepository;

    @Autowired SubjectService subjectService;

    public void save(OCSP ocsp) {
        ocspRepository.save(ocsp);
    }

    public boolean checkCertificate(X509Certificate certificate) throws NullPointerException {
        OCSP revokedCert = ocspRepository.findOneBySerialNumber(certificate.getSerialNumber());
        if (revokedCert.isWithdrawn()) {
            System.out.println("Povucen je sertifikat" + revokedCert.getSerialNumber());
            return false;
        }
        return true;

    }

    public Boolean checkParents(X509Certificate certificate) throws CertificateEncodingException, NoSuchAlgorithmException, InvalidKeyException, SignatureException {
        X509Certificate parent = null;
        KeyStoreWriter kw=new KeyStoreWriter();
        char[] array = "tim17".toCharArray();

        X500Name x500name = new JcaX509CertificateHolder(certificate).getIssuer();
        RDN uid = x500name.getRDNs(BCStyle.UID)[0];
        String alias = IETFUtils.valueToString(uid.getFirst().getValue());
        System.out.println(alias);

        kw.loadKeyStore("interCertificate.jks",array);
        KeyStoreReader kr = new KeyStoreReader();
        parent = (X509Certificate) kr.readCertificate("interCertificate.jks", "tim17", alias);
        RDN ou = x500name.getRDNs(BCStyle.OU)[0];
        String organisationUnit = IETFUtils.valueToString(ou.getFirst().getValue());

        boolean validity;
        boolean validityDate;
        validity = checkCertificate(certificate);
        String currentDate = java.time.LocalDate.now().toString();
        validityDate = checkDate(certificate);
        boolean checkRoot = false;
        boolean root = false;

        if(organisationUnit.equalsIgnoreCase("admin")){
            root = true;
            kw.loadKeyStore("root.jks",array);
            parent = (X509Certificate) kr.readCertificate("root.jks", "tim17", alias);
            checkRoot = checkDate(parent);
        }

        PublicKey pk = parent.getPublicKey();
        byte[] dataToSign = certificate.getEncoded();
        byte[] signature = certificate.getSignature();
        /*boolean signVerification = verify(dataToSign,signature,pk);

        if(!signVerification) {
            return false;
        }*/

        if(!validity)
            return false;


        if(!validityDate)
            return false;
        else if(checkRoot == false && root == true)
            return false;
        else if(checkRoot == true && root == true)
            return true;
        else
            return checkParents(parent);

    }

    private boolean checkDate(X509Certificate certificate){
        //SimpleDateFormat iso8601Formater = new SimpleDateFormat("yyyy-MM-dd");
        /*if(certificate == null){
            return false;
        }*/
        Date certDate = certificate.getNotAfter();
        final Instant now = Instant.now();
        final Date nowDate = Date.from(now);
        if(certDate.compareTo(nowDate) > 0) {
            System.out.println(certDate);
            System.out.println(nowDate);
            return true;
        }
        else {
            System.out.println("Sertifikat je istekao");
            return false;
        }
    }

    private boolean verify(byte[] data, byte[] signature, PublicKey publicKey) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException {
        Signature sig = Signature.getInstance("SHA256withRSA");
        sig.initVerify(publicKey);
        sig.update(data);
        return sig.verify(signature);
    }

    public void revokeCertificate(X509Certificate cert) throws CertificateEncodingException {
        OCSP ocsp = ocspRepository.findOneBySerialNumber(cert.getSerialNumber());
        ocsp.setWithdrawn(true);
        ocspRepository.save(ocsp);
        //X500Name x500Name = new JcaX509CertificateHolder(cert).getSubject();
        //RDN uid = x500Name.getRDNs(BCStyle.UID)[0];
        //String id = IETFUtils.valueToString(uid.getFirst().getValue());
    }
}
