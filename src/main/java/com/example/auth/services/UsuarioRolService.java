package com.example.auth.services;

import com.example.auth.models.Rol;
import com.example.auth.models.RolEnum;
import com.example.auth.models.Usuario;
import com.example.auth.models.UsuarioRol;
import com.example.auth.repositories.UsuarioRolRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsuarioRolService {

    private final UsuarioRolRepository usuarioRolRepository;

    private final UsuarioService usuarioService;

    private final RolService rolService;

    public UsuarioRolService(UsuarioRolRepository usuarioRolRepository, UsuarioService usuarioService,
            RolService rolService) {
        this.usuarioRolRepository = usuarioRolRepository;
        this.usuarioService = usuarioService;
        this.rolService = rolService;
    }

    private UsuarioRol buildUsuarioRol(Usuario usuario, Rol rol) {
        UsuarioRol usuarioRol = new UsuarioRol();
        usuarioRol.setUsuario(usuario);
        usuarioRol.setRol(rol);

        return usuarioRol;
    }

    @Transactional(rollbackFor = Exception.class)
    public void saveUsuarioRol(Usuario usuario, List<RolEnum> roles) {
        Usuario usuarioSaved = usuarioService.saveUsuario(usuario);

        List<UsuarioRol> usuarioRolList = roles.stream()
                .map(rolEnum -> buildUsuarioRol(usuarioSaved, rolService.getRolByName(rolEnum)))
                .collect(Collectors.toList());

        usuarioRolRepository.saveAll(usuarioRolList);
    }

    @Transactional(readOnly = true)
    public Usuario getUserByUsername(String username) {
        return usuarioService.getUserByUsername(username);
    }

    @Transactional(readOnly = true)
    public List<Rol> getRolesByUsername(String username) {
        Usuario usuario = usuarioService.getUserByUsername(username);

        return usuario.getUsuarioRolList().stream()
                .map(UsuarioRol::getRol)
                .collect(Collectors.toList());
    }
}
