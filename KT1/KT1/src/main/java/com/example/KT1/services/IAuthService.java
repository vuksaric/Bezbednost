package com.example.KT1.services;

import com.example.KT1.dto.request.ChangePasswordRequest;
import com.example.KT1.dto.request.ForgotPasswordRequest;
import com.example.KT1.dto.request.LoginRequest;
import com.example.KT1.dto.request.RegistrationRequest;
import com.example.KT1.dto.response.UserResponse;

public interface IAuthService {
    UserResponse login(LoginRequest request);

    UserResponse registerSubject(RegistrationRequest request);

    void forgotPassword(ForgotPasswordRequest request);

    void changePassword(ChangePasswordRequest request);
}
