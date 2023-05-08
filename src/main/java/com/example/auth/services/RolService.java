package com.example.auth.services;

import com.example.auth.exceptions.NotFoundException;
import com.example.auth.models.Rol;
import com.example.auth.models.RolEnum;
import com.example.auth.repositories.RolRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RolService {

    private final RolRepository rolRepository;

    public RolService(RolRepository rolRepository) {
        this.rolRepository = rolRepository;
    }

    @Transactional(readOnly = true)
    public Rol getRolByName(RolEnum rolEnum) {
        return rolRepository.findByRol(rolEnum)
                .orElseThrow(() -> new NotFoundException(
                        String.format("El rol %s no existe", rolEnum.getNombre()),
                        "ERR-001"));
    }
}
