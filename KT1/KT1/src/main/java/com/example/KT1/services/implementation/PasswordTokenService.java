package com.example.KT1.services.implementation;

import com.example.KT1.model.PasswordToken;
import com.example.KT1.model.VerificationToken;
import com.example.KT1.repository.PasswordTokenRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class PasswordTokenService {
    private final PasswordTokenRepository _ptRepository;

    public PasswordTokenService(PasswordTokenRepository ptRepository) {
        _ptRepository = ptRepository;
    }


    public PasswordToken createToken(String email){
        PasswordToken passwordToken = new PasswordToken();
        passwordToken.setEmail(email);
        passwordToken.setExpiryDate(passwordToken.calculateExpiryDate(24*60));
        passwordToken.setToken( UUID.randomUUID().toString());
        return _ptRepository.save(passwordToken);
    }
}
