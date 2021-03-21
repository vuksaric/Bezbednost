package com.example.KT1.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ExtensionDTO {
    private Boolean digitalSignature;
    private Boolean keyEncipherment;
    private Boolean keyAgreement;
    private Boolean emailProtection;
    private Boolean nonRepudiation;
    private String codeSigning;

}
