package com.example.KT1.dto.response;

import com.example.KT1.util.enums.UserRoles;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {

    private Long id;
    private String username;
    private String token;
    private int tokenExpiresIn;
    private String userRoles;
}
