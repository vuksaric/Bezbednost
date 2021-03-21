package com.example.KT1.certificate;

import java.math.BigInteger;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;

import com.example.KT1.dto.ExtensionDTO;
import com.example.KT1.keyStore.KeyStoreReader;
import com.example.KT1.keyStore.KeyStoreWriter;
import com.example.KT1.model.Issuer;
import com.example.KT1.model.Subject;
import org.bouncycastle.asn1.oiw.OIWObjectIdentifiers;
import org.bouncycastle.asn1.x500.X500NameBuilder;
import org.bouncycastle.asn1.x500.style.BCStyle;
import org.bouncycastle.asn1.x509.*;
import org.bouncycastle.cert.CertIOException;
import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.cert.X509ExtensionUtils;
import org.bouncycastle.cert.X509v3CertificateBuilder;
import org.bouncycastle.cert.jcajce.JcaX509CertificateConverter;
import org.bouncycastle.cert.jcajce.JcaX509v3CertificateBuilder;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.operator.ContentSigner;
import org.bouncycastle.operator.DigestCalculator;
import org.bouncycastle.operator.OperatorCreationException;
import org.bouncycastle.operator.bc.BcDigestCalculatorProvider;
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder;


public class CertificateGenerator {
    public CertificateGenerator() {}


    public static X509Certificate generateCertRoot(Subject subject, Issuer issuer, KeyPair keyPair, String hashAlgorithm, int numDays, ExtensionDTO extensionDTO) throws OperatorCreationException, CertIOException, CertificateException {
        X500NameBuilder nameBuilder = new X500NameBuilder(BCStyle.INSTANCE);
        nameBuilder.addRDN(BCStyle.CN, subject.getName() + subject.getSurname());
        nameBuilder.addRDN(BCStyle.SURNAME, subject.getSurname());
        nameBuilder.addRDN(BCStyle.GIVENNAME, subject.getName());
        nameBuilder.addRDN(BCStyle.O, subject.getOrganisation());
        nameBuilder.addRDN(BCStyle.OU, subject.getOrganisationUnit());
        nameBuilder.addRDN(BCStyle.E, subject.getEmail());
        nameBuilder.addRDN(BCStyle.UID, subject.getId().toString());

        final Instant now = Instant.now();
        final Date startDate = Date.from(now);
        final Date endDate = Date.from(now.plus(Duration.ofDays(numDays)));


        PrivateKey pk = keyPair.getPrivate();
        final ContentSigner contentSigner = new JcaContentSignerBuilder(hashAlgorithm).build(pk);

        Boolean isCa = subject.isCA();

        boolean dig =   extensionDTO.getDigitalSignature() == true ? true : false;
        boolean non =   extensionDTO.getNonRepudiation() == true ? true : false;
        boolean enc =   extensionDTO.getKeyEncipherment() == true ? true : false;
        boolean dataEnc =   extensionDTO.getDigitalSignature() == true ? true : false;
        boolean agr =   extensionDTO.getKeyAgreement() == true ? true : false;
        //boolean keyCertSign =   extensionDTO.getNonRepudiation() == true ? true : false;
        boolean crl =   extensionDTO.getKeyAgreement() == true ? true : false;

        int sum = 0;
        sum += 4;
        if(dig)
            sum += 128;
        if(non)
            sum += 64;
        if(enc)
            sum += 32;
        if(dataEnc)
            sum += 16;
        if(agr)
            sum += 8;
        if(crl)
            sum += 2;

        KeyUsage keyUsage = new KeyUsage(sum);


        final X509v3CertificateBuilder certificateBuilder =
                new JcaX509v3CertificateBuilder( nameBuilder.build(),
                        BigInteger.valueOf(now.toEpochMilli()),
                        startDate,
                        endDate,
                        nameBuilder.build(),
                        keyPair.getPublic())
                        .addExtension(Extension.subjectKeyIdentifier, false, createSubjectKeyId(keyPair.getPublic()))
                        .addExtension(Extension.authorityKeyIdentifier, false, createAuthorityKeyId(keyPair.getPublic()))
                        .addExtension(Extension.basicConstraints, true, new BasicConstraints(isCa))
                        .addExtension(Extension.keyUsage, true, keyUsage);


        return new JcaX509CertificateConverter()
                .setProvider(new BouncyCastleProvider()).getCertificate(certificateBuilder.build(contentSigner));

    }

    public java.security.cert.Certificate generateCertFromRoot(Subject subject, Issuer issuer, KeyPair keyPair, String hashAlgorithm, int numDays, ExtensionDTO extensionDTO) throws OperatorCreationException, CertIOException, CertificateException {
        X500NameBuilder nameBuilder = new X500NameBuilder(BCStyle.INSTANCE);
        nameBuilder.addRDN(BCStyle.CN, subject.getName() + subject.getSurname());
        nameBuilder.addRDN(BCStyle.SURNAME, subject.getSurname());
        nameBuilder.addRDN(BCStyle.GIVENNAME, subject.getName());
        nameBuilder.addRDN(BCStyle.O, subject.getOrganisation());
        nameBuilder.addRDN(BCStyle.OU, subject.getOrganisationUnit());
        nameBuilder.addRDN(BCStyle.E, subject.getEmail());
        nameBuilder.addRDN(BCStyle.UID, subject.getId().toString());

        final Instant now = Instant.now();
        final Date startDate = Date.from(now);
        final Date endDate = Date.from(now.plus(Duration.ofDays(numDays)));

        KeyStoreWriter kw = new KeyStoreWriter();
        char[] array = "tim17".toCharArray();
        KeyStoreReader kr = new KeyStoreReader();
        kw.loadKeyStore("rootCertificate.jks", array);
        PrivateKey pk = kr.readPrivateKey("rootCertificate.jks", "tim17", issuer.getId().toString(), issuer.getId().toString());
        // alijas - pokazuje na sertifikat, password - otkljucava sertifikat sa odg alijasom, keyStorePass - otkljucava keyStore
        System.out.println(pk);
        final ContentSigner contentSigner = new JcaContentSignerBuilder(hashAlgorithm).build(pk);

        Boolean isCa = subject.isCA();

        boolean dig = extensionDTO.getDigitalSignature() == true ? true : false;
        boolean non = extensionDTO.getNonRepudiation() == true ? true : false;
        boolean enc = extensionDTO.getKeyEncipherment() == true ? true : false;
        boolean dataEnc = extensionDTO.getDigitalSignature() == true ? true : false;
        boolean agr = extensionDTO.getKeyAgreement() == true ? true : false;
        //boolean keyCertSign =   extensionDTO.getNonRepudiation() == true ? true : false;
        boolean crl = extensionDTO.getKeyAgreement() == true ? true : false;

        int sum = 0;
        sum += 4;
        if (dig)
            sum += 128;
        if (non)
            sum += 64;
        if (enc)
            sum += 32;
        if (dataEnc)
            sum += 16;
        if (agr)
            sum += 8;
        if (crl)
            sum += 2;

        KeyUsage keyUsage = new KeyUsage(sum);

        X509Certificate certRoot = (X509Certificate) kr.readCertificate("rootCertificate.jks", "tim17", issuer.getId().toString());
        Date certRDate = certRoot.getNotAfter();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        if (certRDate.compareTo(endDate) > 0) {
            System.out.println("veci je");
        }


            System.out.println("usao");
            final X509v3CertificateBuilder certificateBuilder =
                    new JcaX509v3CertificateBuilder(certRoot,
                            BigInteger.valueOf(now.toEpochMilli()),
                            startDate,
                            endDate,
                            nameBuilder.build(),
                            keyPair.getPublic())
                            .addExtension(Extension.subjectKeyIdentifier, false, createSubjectKeyId(keyPair.getPublic()))
                            .addExtension(Extension.authorityKeyIdentifier, false, createAuthorityKeyId(certRoot.getPublicKey()))
                            .addExtension(Extension.basicConstraints, true, new BasicConstraints(isCa))
                            .addExtension(Extension.keyUsage, true, keyUsage);

        return new JcaX509CertificateConverter()
                .setProvider(new BouncyCastleProvider()).getCertificate(certificateBuilder.build(contentSigner));

    }

    public java.security.cert.Certificate generateCertFromInter(Subject subject, Subject issuer, KeyPair keyPair, String hashAlgorithm, int numDays, ExtensionDTO extensionDTO) throws OperatorCreationException, CertIOException, CertificateException {
        X500NameBuilder nameBuilder = new X500NameBuilder(BCStyle.INSTANCE);
        nameBuilder.addRDN(BCStyle.CN, subject.getName() + subject.getSurname());
        nameBuilder.addRDN(BCStyle.SURNAME, subject.getSurname());
        nameBuilder.addRDN(BCStyle.GIVENNAME, subject.getName());
        nameBuilder.addRDN(BCStyle.O, subject.getOrganisation());
        nameBuilder.addRDN(BCStyle.OU, subject.getOrganisationUnit());
        nameBuilder.addRDN(BCStyle.E, subject.getEmail());
        nameBuilder.addRDN(BCStyle.UID, subject.getId().toString());

        final Instant now = Instant.now();
        final Date startDate = Date.from(now);
        final Date endDate = Date.from(now.plus(Duration.ofDays(numDays)));

        KeyStoreWriter kw = new KeyStoreWriter();
        char[] array = "tim17".toCharArray();
        KeyStoreReader kr = new KeyStoreReader();
        kw.loadKeyStore("intermediateCertificate.jks", array);
        PrivateKey pk = kr.readPrivateKey("intermediateCertificate.jks", "tim17", issuer.getId().toString(), issuer.getId().toString());
        // alijas - pokazuje na sertifikat, password - otkljucava sertifikat sa odg alijasom, keyStorePass - otkljucava keyStore
        System.out.println(pk);
        final ContentSigner contentSigner = new JcaContentSignerBuilder(hashAlgorithm).build(pk);

        Boolean isCa = subject.isCA();

        boolean dig = extensionDTO.getDigitalSignature() == true ? true : false;
        boolean non = extensionDTO.getNonRepudiation() == true ? true : false;
        boolean enc = extensionDTO.getKeyEncipherment() == true ? true : false;
        boolean dataEnc = extensionDTO.getDigitalSignature() == true ? true : false;
        boolean agr = extensionDTO.getKeyAgreement() == true ? true : false;
        //boolean keyCertSign =   extensionDTO.getNonRepudiation() == true ? true : false;
        boolean crl = extensionDTO.getKeyAgreement() == true ? true : false;

        int sum = 0;
        sum += 4;
        if (dig)
            sum += 128;
        if (non)
            sum += 64;
        if (enc)
            sum += 32;
        if (dataEnc)
            sum += 16;
        if (agr)
            sum += 8;
        if (crl)
            sum += 2;

        KeyUsage keyUsage = new KeyUsage(sum);

        X509Certificate certRoot = (X509Certificate) kr.readCertificate("rootCertificate.jks", "tim17", issuer.getId().toString());
        Date certRDate = certRoot.getNotAfter();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        if (certRDate.compareTo(endDate) > 0) {
            System.out.println("veci je");
        }


        System.out.println("usao");
        final X509v3CertificateBuilder certificateBuilder =
                new JcaX509v3CertificateBuilder(certRoot,
                        BigInteger.valueOf(now.toEpochMilli()),
                        startDate,
                        endDate,
                        nameBuilder.build(),
                        keyPair.getPublic())
                        .addExtension(Extension.subjectKeyIdentifier, false, createSubjectKeyId(keyPair.getPublic()))
                        .addExtension(Extension.authorityKeyIdentifier, false, createAuthorityKeyId(certRoot.getPublicKey()))
                        .addExtension(Extension.basicConstraints, true, new BasicConstraints(isCa))
                        .addExtension(Extension.keyUsage, true, keyUsage);

        return new JcaX509CertificateConverter()
                .setProvider(new BouncyCastleProvider()).getCertificate(certificateBuilder.build(contentSigner));

    }

    private static SubjectKeyIdentifier createSubjectKeyId(final PublicKey publicKey) throws OperatorCreationException {
        final SubjectPublicKeyInfo publicKeyInfo = SubjectPublicKeyInfo.getInstance(publicKey.getEncoded());
        final DigestCalculator digCalc =
                new BcDigestCalculatorProvider().get(new AlgorithmIdentifier(OIWObjectIdentifiers.idSHA1));

        return new X509ExtensionUtils(digCalc).createSubjectKeyIdentifier(publicKeyInfo);
    }

    private static AuthorityKeyIdentifier createAuthorityKeyId(final PublicKey publicKey)
            throws OperatorCreationException
    {
        final SubjectPublicKeyInfo publicKeyInfo = SubjectPublicKeyInfo.getInstance(publicKey.getEncoded());
        final DigestCalculator digCalc =
                new BcDigestCalculatorProvider().get(new AlgorithmIdentifier(OIWObjectIdentifiers.idSHA1));

        return new X509ExtensionUtils(digCalc).createAuthorityKeyIdentifier(publicKeyInfo);
    }


}
