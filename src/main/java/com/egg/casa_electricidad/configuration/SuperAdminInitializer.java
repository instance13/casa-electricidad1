package com.egg.casa_electricidad.configuration;

import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.egg.casa_electricidad.configuration.dto.request.SuperAdminRequestDTO;
import com.egg.casa_electricidad.enumeraciones.Rol;
import com.egg.casa_electricidad.servicios.UsuarioServicio;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Component
public class SuperAdminInitializer {
  private final PasswordEncoder passwordEncoder;

  @Bean
  public ApplicationRunner initSuperAdmin(UsuarioServicio usuarioServicio) {
    return args -> {
      if (!usuarioServicio.existeUsuarioPorRol(Rol.ADMIN)) {
        SuperAdminRequestDTO superAdmin = new SuperAdminRequestDTO();

        System.out.println("----------- Super Admin: " + superAdmin.getEmail()+ " "+superAdmin.getPassword());

        superAdmin.setEmail("admin@casaelectricidad.com");
        superAdmin.setPassword("admin123/*/");
        superAdmin.setRol(Rol.ADMIN);

        usuarioServicio.crear(superAdmin);
      }
    };
  }
}
