package com.example.KT1.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.security.PublicKey;
import java.util.Date;
import org.bouncycastle.asn1.x500.X500Name;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Subject {

    @Id
    @SequenceGenerator(name = "users_id_seq", sequenceName = "users_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "users_id_seq")
    private Long id;
    private String name;
    private String surname;
    private String email;
    private String organisation;
    private String organisationUnit;
    private boolean isCA = false;
    private boolean certificate;


    public Subject(Issuer issuer) {
        name = issuer.getName();
        surname = issuer.getSurname();
        email = issuer.getEmail();
        organisation = issuer.getOrganisation();
        organisationUnit = issuer.getOrganisationUnit();
        isCA = true;
        certificate = true;
    }
}
