package com.example.auth.security.models;

import com.example.auth.models.Usuario;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.stream.Collectors;

@AllArgsConstructor
public class UserDetailsImpl implements UserDetails {

    private final Usuario usuario;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return usuario.getUsuarioRolList().stream()
                .map(usuarioRol -> new SimpleGrantedAuthority(String.format("ROLE_%s", usuarioRol.getRol().getRol().getNombre())))
                .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return usuario.getPassword();
    }

    @Override
    public String getUsername() {
        return usuario.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return !usuario.getCuentaExpirada();
    }

    @Override
    public boolean isAccountNonLocked() {
        return !usuario.getCuentaBloqueada();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return !usuario.getCredencialesExpiradas();
    }

    @Override
    public boolean isEnabled() {
        return usuario.getActivo();
    }

    public String getNombres() {
        return usuario.getNombres();
    }

    public String getApellidos() {
        return usuario.getApellidos();
    }
}
