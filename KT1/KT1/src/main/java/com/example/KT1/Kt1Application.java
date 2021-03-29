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
		//X509Certificate x509Certificate2 = certificateGenerator1.generateCertificate(subjectData1, issuerData);

		KeyStoreWriter keyStore = new KeyStoreWriter();
		KeyStoreWriter keyStore1 = new KeyStoreWriter();
		KeyStoreWriter keyStore2 = new KeyStoreWriter();

		char[] password = "tim17".toCharArray();

		keyStore.loadKeyStore(null, password);
		keyStore1.loadKeyStore(null, password);
		keyStore2.loadKeyStore(null, password);

		keyStore.saveKeyStore("root.jks", password);

		keyStore1.saveKeyStore("interCertificate.jks", password);

		keyStore2.saveKeyStore("endEntity.jks", password);
	}

}
