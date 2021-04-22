package com.example.KT1.controller;

import com.example.KT1.dto.request.LoginRequest;
import com.example.KT1.dto.request.RegistrationRequest;
import com.example.KT1.dto.response.UserResponse;
import com.example.KT1.services.IAuthService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final IAuthService _authService;

    public AuthController(IAuthService authService) {
        _authService = authService;
    }


    @PutMapping("/login")
    //@PreAuthorize("hasAuthority('LOGIN')")
    public UserResponse login(@RequestBody LoginRequest request){
        return _authService.login(request);
    }

    @PostMapping("/register")
    //@PreAuthorize("hasAuthority('REGISTER')")
    public UserResponse registerSubject(@RequestBody RegistrationRequest request){
        return _authService.registerSubject(request);
    }

}

