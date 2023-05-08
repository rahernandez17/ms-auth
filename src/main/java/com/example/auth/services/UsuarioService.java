package com.example.auth.services;

import com.example.auth.exceptions.NotFoundException;
import com.example.auth.mappers.UsuarioMapper;
import com.example.auth.models.Usuario;
import com.example.auth.repositories.UsuarioRepository;
import com.example.auth.responses.UsuarioResponse;
import com.example.auth.security.components.TokenUtilComponent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;

@Service
public class UsuarioService {

    @Value("${jwt.prefix}")
    private String jwtPrefix;

    private String prefix;

    private final UsuarioRepository usuarioRepository;

    private final TokenUtilComponent tokenUtilComponent;

    private final UsuarioMapper usuarioMapper;

    public UsuarioService(
            UsuarioRepository usuarioRepository,
            TokenUtilComponent tokenUtilComponent,
            UsuarioMapper usuarioMapper) {
        this.usuarioRepository = usuarioRepository;
        this.tokenUtilComponent = tokenUtilComponent;
        this.usuarioMapper = usuarioMapper;
    }

    @PostConstruct
    public void init() {
        prefix = String.format("%s ", jwtPrefix);
    }

    @Transactional(readOnly = true)
    public UsuarioResponse getByAccessToken(String token) {
        String username = tokenUtilComponent.extractUsername(token.replace(prefix, ""));
        return usuarioMapper.entityToResponse(getUserByUsername(username));
    }

    @Transactional(readOnly = true)
    public Usuario getUserByUsername(String username) {
        return usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new NotFoundException(
                        String.format("El usuario %s no existe", username),
                        "ERR-002"));
    }

    @Transactional(rollbackFor = Exception.class)
    public Usuario saveUsuario(Usuario usuario) {
        usuario.setActivo(true);
        usuario.setCredencialesExpiradas(false);
        usuario.setCuentaBloqueada(false);
        usuario.setCuentaExpirada(false);

        return usuarioRepository.save(usuario);
    }
}
