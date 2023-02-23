package com.example.auth.mappers;

import com.example.auth.models.Rol;
import com.example.auth.models.UsuarioRol;
import com.example.auth.responses.RolResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RolMapper {

    @Mapping(target = ".", source = "rol")
    Rol usuarioRolToRol(UsuarioRol usuarioRol);

    List<Rol> listUsuarioRolToListRol(List<UsuarioRol> usuarioRoles);

    RolResponse entityToResponse(Rol rol);

    List<RolResponse> listEntityToListResponse(List<Rol> rol);

}
