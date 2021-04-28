package com.example.KT1.services.implementation;

import com.example.KT1.model.VerificationToken;
import com.example.KT1.repository.VerificationTokenRepository;
import com.google.common.hash.Hashing;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;


@Service
public class VerificationTokenService {

    private final VerificationTokenRepository _vtRepository;

    public VerificationTokenService(VerificationTokenRepository vtRepository) {
        _vtRepository = vtRepository;
    }


    public VerificationToken createToken(String email) throws NoSuchAlgorithmException {
        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setEmail(email);
        verificationToken.setExpiryDate(verificationToken.calculateExpiryDate(24*60));
        String sha256hex = Hashing.sha256()
                .hashString(email, StandardCharsets.UTF_8)
                .toString();

        verificationToken.setToken(sha256hex);
        return _vtRepository.save(verificationToken);
    }

    public String validationConfirmation(String token){
        List<VerificationToken> verificationTokenList  = _vtRepository.findAll();
        for (VerificationToken value :verificationTokenList) {
            if(token.equals(value.getToken())){
                return value.getEmail();
            }
        }
        return null;
    }
}
