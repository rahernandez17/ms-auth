package com.example.auth.models;


import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum RolEnum {
    ADMIN("ADMIN"),
    USER("USER");

    private final String nombre;
}
