package com.egg.casa_electricidad;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.egg.casa_electricidad.servicios.UsuarioServicio;

@Configuration

public class SeguridadWeb {

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public UserDetailsService userDetailsService() {
    return new UsuarioServicio();
  }

  @Bean
  public AuthenticationManager authenticationManager() {

    DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
    provider.setUserDetailsService(userDetailsService());
    provider.setPasswordEncoder(passwordEncoder());

    return new ProviderManager(provider);
  }

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http
        .authorizeHttpRequests((authorize) -> authorize
            .requestMatchers("/admin/**").hasRole("ADMIN")
            .requestMatchers("/css/**", "/js/**", "/img/**").permitAll()
            .requestMatchers("/login", "/registro", "/registrar").permitAll()
            .anyRequest().authenticated() // Requiere autenticacion
        ).sessionManagement(session -> session
            .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
            // Optional: set max sessions per user
            .maximumSessions(1)
            // Optional: prevent login if max sessions reached
            .maxSessionsPreventsLogin(false))
        .formLogin((form) -> form
            .loginPage("/login")
            .loginProcessingUrl("/logincheck")
            .usernameParameter("email")
            .passwordParameter("password")
            .defaultSuccessUrl("/inicio", true)
            .permitAll())
        .logout((logout) -> logout
            .logoutUrl("/logout")
            .logoutSuccessUrl("/")
            .permitAll())
        .csrf(csrf -> csrf.disable());
    return http.build();
  }
}
