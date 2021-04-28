package com.example.KT1.dto.request;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ChangePasswordRequest {
    private String email;
    private String password;
    private String rePassword;

}
