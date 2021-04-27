package com.example.KT1.dto.response;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubjectResponse {
    private Long id;
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String organisation;
    private String organisationUnit;

}
