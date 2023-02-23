package com.example.auth.responses;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthenticationResponse {

    private String nombres;

    private String apellidos;

    private String username;

    private List<RolResponse> roles;

    private String token;
}
