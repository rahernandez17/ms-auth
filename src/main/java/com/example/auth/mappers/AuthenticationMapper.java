package com.example.auth.mappers;

import com.example.auth.models.Usuario;
import com.example.auth.requests.RegisterUserRequest;
import com.example.auth.responses.AuthenticationResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {
        RolMapper.class
})
public interface AuthenticationMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "activo", ignore = true)
    @Mapping(target = "cuentaExpirada", ignore = true)
    @Mapping(target = "cuentaBloqueada", ignore = true)
    @Mapping(target = "credencialesExpiradas", ignore = true)
    @Mapping(target = "usuarioRolList", ignore = true)
    Usuario usuarioToRegisterUserRequest(RegisterUserRequest request);

    @Mapping(target = "token", ignore = true)
    @Mapping(target = "roles", source = "usuarioRolList")
    AuthenticationResponse entityToAuthenticationRequest(Usuario usuario);

}
