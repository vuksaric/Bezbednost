package com.example.KT1.controller;

import com.example.KT1.dto.request.GetIdRequest;
import com.example.KT1.services.implementation.AttackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/attack")
public class AttackController {

    @Autowired
    AttackService attackService;

    @PutMapping("/email")
    //@PreAuthorize("hasAuthority('REGISTER')")
    public boolean validateEmail(@RequestBody String input){
        return attackService.emailValidation(input);
    }

    @PutMapping("/password")
    //@PreAuthorize("hasAuthority('REGISTER')")
    public boolean validatePassword(@RequestBody String input){
        return attackService.passwordValidation(input);
    }

    @PutMapping("/name")
    //@PreAuthorize("hasAuthority('REGISTER')")
    public boolean validateName(@RequestBody String input){
        return attackService.nameValidation(input);
    }

    @PutMapping("/escape")
    //@PreAuthorize("hasAuthority('REGISTER')")
    public String charachterEscaping(@RequestBody String input){
        return attackService.escaping(input);
    }

}
