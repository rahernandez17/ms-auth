package com.example.auth.services;

import com.example.auth.mappers.AuthenticationMapper;
import com.example.auth.models.Rol;
import com.example.auth.models.RolEnum;
import com.example.auth.models.Usuario;
import com.example.auth.requests.AuthenticationRequest;
import com.example.auth.requests.RegisterUserRequest;
import com.example.auth.responses.AuthenticationResponse;
import com.example.auth.responses.RefreshTokenResponse;
import com.example.auth.security.components.TokenUtilComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class AuthenticationService {

    @Value("${jwt.prefix}")
    private String jwtPrefix;

    private String prefix;

    private final UsuarioRolService usuarioRolService;

    private final PasswordEncoder passwordEncoder;

    private final TokenUtilComponent tokenUtilComponent;

    private final AuthenticationManager authenticationManager;

    private final AuthenticationMapper authenticationMapper;

    @Autowired
    public AuthenticationService(
            UsuarioRolService usuarioRolService,
            PasswordEncoder passwordEncoder,
            TokenUtilComponent tokenUtilComponent,
            AuthenticationManager authenticationManager,
            AuthenticationMapper authenticationMapper
    ) {
        this.usuarioRolService = usuarioRolService;
        this.passwordEncoder = passwordEncoder;
        this.tokenUtilComponent = tokenUtilComponent;
        this.authenticationManager = authenticationManager;
        this.authenticationMapper = authenticationMapper;
    }

    @PostConstruct
    public void init() {
        prefix = String.format("%s ", jwtPrefix);
    }

    private Map<String,Object> buildClaims(String username, List<RolEnum> roles) {
        return Map.ofEntries(
                Map.entry("username", username),
                Map.entry("roles", roles)
        );
    }

    @Transactional(rollbackFor = Exception.class)
    public void register(RegisterUserRequest request) {
        Usuario usuario = authenticationMapper.usuarioToRegisterUserRequest(request);
        usuario.setPassword(passwordEncoder.encode(request.getPassword()));

        usuarioRolService.saveUsuarioRol(usuario, request.getRoles());
    }

    @Transactional(rollbackFor = Exception.class)
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
          new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );

        Usuario usuario = usuarioRolService.getUserByUsername(request.getUsername());

        List<RolEnum> rolesEnum = usuario.getUsuarioRolList().stream()
                .map(usuarioRol -> usuarioRol.getRol().getRol())
                .collect(Collectors.toList());

        AuthenticationResponse authenticationResponse = authenticationMapper.entityToAuthenticationRequest(usuario);
        authenticationResponse.setToken(tokenUtilComponent
                .generateToken(buildClaims(usuario.getUsername(), rolesEnum), usuario.getUsername()));

        return authenticationResponse;
    }

    @Transactional(rollbackFor = Exception.class)
    public RefreshTokenResponse refreshToken(String token) {
        final String username = tokenUtilComponent.extractUsername(token.replace(prefix, ""));
        List<Rol> roles = usuarioRolService.getRolesByUsername(username);

        List<RolEnum> rolesEnum = roles.stream()
                .map(Rol::getRol)
                .collect(Collectors.toList());

       return RefreshTokenResponse.builder()
               .token(tokenUtilComponent.generateToken(buildClaims(username, rolesEnum), username))
               .build();
    }
}
