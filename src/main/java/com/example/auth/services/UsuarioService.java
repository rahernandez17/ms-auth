package com.example.auth.services;

import com.example.auth.exceptions.NotFoundException;
import com.example.auth.models.Usuario;
import com.example.auth.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    @Autowired
    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Transactional(readOnly = true)
    public List<Usuario> getAllUsers() {
        return usuarioRepository.findAll();
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
