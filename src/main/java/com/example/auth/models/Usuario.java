package com.example.auth.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "usuarios")
public class Usuario {

    @Id
    @Column(name = "usuario_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String nombres;

    @Column(nullable = false, length = 100)
    private String apellidos;

    @Column(nullable = false, length = 100)
    private String username;

    @Column(nullable = false, length = 300)
    private String password;

    @Column(nullable = false)
    private Boolean activo;

    @Column(name = "cuenta_expirada", nullable = false)
    private Boolean cuentaExpirada;

    @Column(name = "cuenta_bloqueada", nullable = false)
    private Boolean cuentaBloqueada;

    @Column(name = "credenciales_expiradas",nullable = false)
    private Boolean credencialesExpiradas;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "usuario", fetch = FetchType.LAZY)
    @Fetch(FetchMode.JOIN)
    @JsonManagedReference
    private List<UsuarioRol> usuarioRolList;
}
