package com.example.KT1.model;

import com.example.KT1.util.enums.RequestStatus;
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
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String surname;
    private String email;
    private String organisation;
    private String organisationUnit;
    private boolean isCA = false;
    private boolean certificate;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @Enumerated(EnumType.STRING)
    private RequestStatus requestStatus;



    public Subject(Issuer issuer) {
        name = issuer.getName();
        surname = issuer.getSurname();
        email = issuer.getEmail();
        organisation = issuer.getOrganisation();
        organisationUnit = "admin";
        isCA = true;
        certificate = true;
    }
}
