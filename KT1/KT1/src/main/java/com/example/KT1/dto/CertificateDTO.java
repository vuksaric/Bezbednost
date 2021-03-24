package com.example.KT1.dto;

import com.example.KT1.model.Issuer;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.bouncycastle.asn1.x500.RDN;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x500.style.BCStyle;
import org.bouncycastle.asn1.x500.style.IETFUtils;
import org.bouncycastle.cert.jcajce.JcaX509CertificateHolder;
import org.bouncycastle.cert.jcajce.JcaX509v3CertificateBuilder;

import java.security.cert.CertificateEncodingException;
import java.security.cert.X509Certificate;
import java.util.Date;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CertificateDTO {

    private String id;
    private String subject;
    @JsonFormat(pattern = "dd/MM/yy")
    private Date startDate;
    @JsonFormat(pattern = "dd/MM/yy")
    private Date endDate;
    private String issuer;
    private String email;
    private String organisation;
    private String organisationUnit;
    private String publicKey;
    private String issuerOrganisationUnit;

    public CertificateDTO(X509Certificate cert) throws CertificateEncodingException {
        X500Name x500Name = new JcaX509CertificateHolder(cert).getSubject();
        RDN cn = x500Name.getRDNs(BCStyle.CN)[0];
        subject = IETFUtils.valueToString(cn.getFirst().getValue());
        startDate = cert.getNotBefore();
        endDate = cert.getNotAfter();
        issuer = cert.getIssuerDN().getName();
        RDN e = x500Name.getRDNs(BCStyle.E)[0];
        email = IETFUtils.valueToString(e.getFirst().getValue());
        RDN o = x500Name.getRDNs(BCStyle.O)[0];
        organisation = IETFUtils.valueToString(o.getFirst().getValue());
        RDN ou = x500Name.getRDNs(BCStyle.OU)[0];
        organisationUnit = IETFUtils.valueToString(ou.getFirst().getValue());
        publicKey = cert.getPublicKey().toString();
        X500Name x500NameIssuer = new JcaX509CertificateHolder(cert).getIssuer();
        RDN ouIssuer = x500NameIssuer.getRDNs(BCStyle.OU)[0];
        issuerOrganisationUnit = IETFUtils.valueToString(ouIssuer.getFirst().getValue());
        RDN uid = x500Name.getRDNs(BCStyle.UID)[0];
        id = IETFUtils.valueToString(uid.getFirst().getValue());
    }
}
