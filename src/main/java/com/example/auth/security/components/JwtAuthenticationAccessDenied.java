package com.example.auth.security.components;

import com.example.auth.responses.SimpleResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Component
public class JwtAuthenticationAccessDenied implements AccessDeniedHandler {

    private ObjectMapper mapper;

    @PostConstruct
    public void init() {
        mapper = new ObjectMapper();
    }

    @Override
    public void handle(
            HttpServletRequest request,
            HttpServletResponse response,
            AccessDeniedException e
    ) throws IOException {
        PrintWriter out = response.getWriter();

        response.setStatus(HttpStatus.FORBIDDEN.value());
        response.setContentType("application/json");

        out.print(mapper.writeValueAsString(SimpleResponse.builder()
                .code(HttpStatus.FORBIDDEN.value())
                .message("El usuario no cuenta con el permiso para acceder a este punto")
                .path(request.getServletPath())
                .build()));
        out.flush();
    }
}