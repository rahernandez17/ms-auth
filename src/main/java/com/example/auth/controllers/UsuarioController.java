package com.example.auth.controllers;

import com.example.auth.models.Usuario;
import com.example.auth.responses.ListSimpleResponse;
import com.example.auth.services.UsuarioService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/usuarios")
@Api(tags = "Users API")
public class UsuarioController {

    private final UsuarioService usuarioService;

    @Autowired
    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @ApiOperation(value = "Método para consultar todos los usuarios")
    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<ListSimpleResponse<Usuario>> getAll() {
        return new ResponseEntity<>(
                ListSimpleResponse.<Usuario>builder()
                        .code(HttpStatus.OK.value())
                        .message("Obtenido con éxito")
                        .value(usuarioService.getAllUsers())
                        .build(),
                HttpStatus.OK
        );
    }

}
