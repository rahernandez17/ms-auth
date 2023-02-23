package com.example.auth.controllers;

import com.example.auth.responses.RefreshTokenResponse;
import com.example.auth.responses.SimpleResponse;
import com.example.auth.requests.AuthenticationRequest;
import com.example.auth.requests.RegisterUserRequest;
import com.example.auth.responses.AuthenticationResponse;
import com.example.auth.services.AuthenticationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequestMapping("/auth")
@Api(tags = "Authentication API")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @Autowired
    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @ApiOperation(value = "Método para registrar un usuario")
    @PostMapping(value = "/register", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<SimpleResponse<AuthenticationResponse>> register(
            @RequestBody RegisterUserRequest request
    ) {
        authenticationService.register(request);

        return ResponseEntity.ok(
                SimpleResponse.<AuthenticationResponse>builder()
                        .code(HttpStatus.OK.value())
                        .message("Registrado con éxito")
                        .build()
        );
    }

    @ApiOperation(value = "Método para autenticar un usuario")
    @PostMapping(value = "/login", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<SimpleResponse<AuthenticationResponse>> authenticate(
            @RequestBody AuthenticationRequest request
    ) {
        return ResponseEntity.ok(
                SimpleResponse.<AuthenticationResponse>builder()
                        .code(HttpStatus.OK.value())
                        .message("Autenticado con éxito")
                        .value(authenticationService.authenticate(request))
                        .build()
        );
    }

    @ApiOperation(value = "Método para refrescar el token")
    @PostMapping(value = "/refresh-token", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<SimpleResponse<RefreshTokenResponse>> refreshToken(
            @ApiIgnore @RequestHeader("Authorization") String token
    ) {
        return ResponseEntity.ok(
                SimpleResponse.<RefreshTokenResponse>builder()
                        .code(HttpStatus.OK.value())
                        .message("Se refrescó con éxito")
                        .value(authenticationService.refreshToken(token))
                        .build()
        );
    }
}
