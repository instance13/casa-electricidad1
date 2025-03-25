package com.egg.casa_electricidad.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SeguridadWeb {

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  // @Bean
  // public UserDetailsService userDetailsService(UsuarioServicio usuarioServicio)
  // {
  // return usuarioServicio;
  // }

  @Bean
  public AuthenticationManager authenticationManager(UserDetailsService userDetailsService,
      PasswordEncoder passwordEncoder) {

    DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
    provider.setUserDetailsService(userDetailsService);
    provider.setPasswordEncoder(passwordEncoder);

    return new ProviderManager(provider);
  }

  // @Bean
  // public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
  // http
  // .authorizeHttpRequests((authorize) -> authorize
  // .requestMatchers("/admin/**").hasRole("ADMIN")
  // .requestMatchers("/css/**", "/js/**", "/img/**").permitAll()
  // .requestMatchers("/login", "/usuario/").permitAll()
  // .anyRequest().authenticated() // Requiere autenticacion
  // ).sessionManagement(session ->
  // session.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
  // // Optional: set max sessions per user
  // .maximumSessions(1)
  // // Optional: prevent login if max sessions reached
  // .maxSessionsPreventsLogin(false))
  // .formLogin((form) -> form
  // .loginPage("/login")
  // .loginProcessingUrl("/logincheck")
  // .usernameParameter("email")
  // .passwordParameter("password")
  // .defaultSuccessUrl("/inicio", true)
  // .permitAll())
  // .logout((logout) -> logout
  // .logoutUrl("/logout")
  // .logoutSuccessUrl("/")
  // .permitAll())
  // .csrf(csrf -> csrf.disable());
  // return http.build();
  // }

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http
        .authorizeHttpRequests((authorize) -> authorize
            .requestMatchers("/admin/**").hasRole("ADMIN")
            .requestMatchers("/css/**", "/js/**", "/img/**").permitAll()
            .requestMatchers(
                "/usuario/**", "/",  "/usuario")
            .permitAll()
            .anyRequest().authenticated())
        .csrf(csrf -> csrf.disable())
        .formLogin(form -> form.disable()).httpBasic(Customizer.withDefaults());// Ensure Basic Authentication is enabled
    
    return http.getOrBuild(); 
  }
  // .sessionManagement(session ->
  // session.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
}
