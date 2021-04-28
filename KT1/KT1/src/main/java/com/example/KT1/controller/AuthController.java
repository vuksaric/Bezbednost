package com.example.KT1.controller;

import com.example.KT1.dto.request.ChangePasswordRequest;
import com.example.KT1.dto.request.ForgotPasswordRequest;
import com.example.KT1.dto.request.LoginRequest;
import com.example.KT1.dto.request.RegistrationRequest;
import com.example.KT1.dto.response.UserResponse;
import com.example.KT1.model.Subject;
import com.example.KT1.model.User;
import com.example.KT1.repository.SubjectRepository;
import com.example.KT1.services.IAuthService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final IAuthService _authService;
    private final SubjectRepository _subjectRepository;

    public AuthController(IAuthService authService, SubjectRepository subjectRepository) {
        _authService = authService;
        _subjectRepository = subjectRepository;
    }


    @PutMapping("/login")
    //@PreAuthorize("hasAuthority('LOGIN')")
    public UserResponse login(@RequestBody LoginRequest request){
        return _authService.login(request);
    }

    @PostMapping("/forgot-password")
    public void forgotPassword(@RequestBody ForgotPasswordRequest request){
         _authService.forgotPassword(request);
    }

    @PutMapping("/change-password")
    public void changePassword(@RequestBody ChangePasswordRequest request){
        _authService.changePassword(request);
    }

    @PostMapping("/register")
    //@PreAuthorize("hasAuthority('REGISTER')")
    public UserResponse registerSubject(@RequestBody RegistrationRequest request){
        return _authService.registerSubject(request);
    }


}

