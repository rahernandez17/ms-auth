package com.example.auth.requests;


import com.example.auth.models.RolEnum;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegisterUserRequest {
    private String nombres;

    private String apellidos;

    private String username;

    private String password;

    private List<RolEnum> roles;

}
