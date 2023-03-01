package com.example.auth.mappers;

import com.example.auth.models.Usuario;
import com.example.auth.responses.UsuarioResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {
        RolMapper.class
})
public interface UsuarioMapper {

    @Mapping(target = "roles", source = "usuarioRolList")
    UsuarioResponse entityToResponse(Usuario usuario);

}
