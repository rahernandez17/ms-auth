package com.example.auth.responses;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UsuarioResponse {

    private String nombres;

    private String apellidos;

    private String username;

    private List<RolResponse> roles;
}
