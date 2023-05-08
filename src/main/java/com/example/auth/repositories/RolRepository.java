package com.example.auth.repositories;

import com.example.auth.models.Rol;
import com.example.auth.models.RolEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RolRepository extends JpaRepository<Rol, Long> {

    Optional<Rol> findByRol(RolEnum rol);

}
