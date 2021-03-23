package com.example.KT1;

import com.example.KT1.certificate.CertificateGenerator;
import com.example.KT1.keyStore.KeyStoreWriter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.security.KeyPair;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.cert.X509Certificate;

@SpringBootApplication
public class Kt1Application {

	public static void main(String[] args) throws KeyStoreException {
		SpringApplication.run(Kt1Application.class, args);

		KeyStoreWriter keyStore = new KeyStoreWriter();
		KeyStoreWriter keyStore1 = new KeyStoreWriter();
		KeyStoreWriter keyStore2 = new KeyStoreWriter();

		char[] password = "tim17".toCharArray();

		keyStore.loadKeyStore(null, password);
		keyStore1.loadKeyStore(null, password);
		keyStore2.loadKeyStore(null, password);

		//keyStore.write("root", keyPairIssuer.getPrivate(), password, x509Certificate);
		keyStore.saveKeyStore("root.jks", password);
		//SubjectData subjectData1 = generateSubjectData();
		//CertificateGenerator certificateGenerator1 = new CertificateGenerator();
		//X509Certificate x509Certificate2 = certificateGenerator1.generateCertificate(subjectData1, issuerData);

		//keyStore1.write("ca", keyPairIssuer.getPrivate(), password, x509Certificate2);
		keyStore1.saveKeyStore("interCertificate.jks", password);

		//SubjectData subjectData2 = generateSubjectDataEndEntity();
		//CertificateGenerator certificateGenerator2 = new CertificateGenerator();
		//X509Certificate x509Certificate3 = certificateGenerator2.generateCertificate(subjectData2, issuerData);

		//keyStore2.write("end-entity", keyPairIssuer.getPrivate(), password, x509Certificate3);
		keyStore2.saveKeyStore("endEntity.jks", password);
	}

}
