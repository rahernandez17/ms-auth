package com.example.auth.controllers;

import com.example.auth.responses.SimpleResponse;
import com.example.auth.responses.UsuarioResponse;
import com.example.auth.services.UsuarioService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequestMapping("/usuarios")
@Api(tags = "Users API")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @ApiOperation(value = "Método para consultar los datos de un usuario")
    @GetMapping(value = "/get-by-access-token", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<SimpleResponse<UsuarioResponse>> getByAccessToken(
            @ApiIgnore @RequestHeader("Authorization") String token
    ) {
        return new ResponseEntity<>(
                SimpleResponse.<UsuarioResponse>builder()
                        .code(HttpStatus.OK.value())
                        .message("Obtenido con éxito")
                        .value(usuarioService.getByAccessToken(token))
                        .build(),
                HttpStatus.OK
        );
    }
}
