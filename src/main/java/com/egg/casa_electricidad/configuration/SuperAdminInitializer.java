package com.egg.casa_electricidad.configuration;

import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.egg.casa_electricidad.configuration.dto.request.SuperAdminRequestDTO;
import com.egg.casa_electricidad.enumeraciones.Rol;
import com.egg.casa_electricidad.repositorios.UsuarioRepositorio;
import com.egg.casa_electricidad.servicios.UsuarioServicio;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class SuperAdminInitializer {
  private final UsuarioServicio usuarioServicio;
  private final UsuarioRepositorio usuarioRepositorio;
  private final PasswordEncoder passwordEncoder;

@Bean
public ApplicationRunner initSuperAdmin() {
    return args -> {
        System.out.println("--------- SuperAdminInitializer started!");

        usuarioRepositorio.findByEmail("admin@casaelectricidad.com")
            .ifPresentOrElse(usuario -> {
                System.out.println("--------- Updating existing admin password...");
                usuario.setPassword(passwordEncoder.encode("admin123/*/"));
                usuarioRepositorio.save(usuario);
            }, () -> {
                System.out.println("---------- Creating new admin...");
                SuperAdminRequestDTO superAdmin = new SuperAdminRequestDTO();
                superAdmin.setEmail("admin@casaelectricidad.com");
                superAdmin.setPassword("admin123/*/");
                superAdmin.setRol(Rol.ADMIN);

                usuarioServicio.crear(superAdmin);
            });
    };
}
  }
