package com.example.KT1.controller;

import com.example.KT1.dto.request.GetIdRequest;
import com.example.KT1.services.implementation.AttackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(value = "/attack")
public class AttackController {

    @Autowired
    AttackService attackService;

    @PostMapping("/email")
    //@PreAuthorize("hasAuthority('REGISTER')")
    public ResponseEntity<?> validateEmail(@RequestBody String input){
        return new ResponseEntity<>(attackService.emailValidation(input), HttpStatus.OK);
    }

    @PostMapping("/password")
    //@PreAuthorize("hasAuthority('REGISTER')")
    public ResponseEntity<?> validatePassword(@RequestBody String input){
        return new ResponseEntity<>(attackService.passwordValidation(input), HttpStatus.OK) ;
    }

    @PostMapping("/name")
    //@PreAuthorize("hasAuthority('REGISTER')")
    public ResponseEntity<?> validateName(@RequestBody String input){
        return new ResponseEntity<>(attackService.nameValidation(input), HttpStatus.OK);
    }

    @PostMapping("/escape")
    //@PreAuthorize("hasAuthority('REGISTER')")
    public ResponseEntity<?> charachterEscaping(@RequestBody String input){
        return new ResponseEntity<>(attackService.escaping(input), HttpStatus.OK);
    }

    @PostMapping("/organisation")
    public ResponseEntity<?> validateOrganisation(@RequestBody String input){
        return new ResponseEntity<>(attackService.organisationValidation(input), HttpStatus.OK);
    }

}
