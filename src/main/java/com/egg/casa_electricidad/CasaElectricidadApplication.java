package com.egg.casa_electricidad;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class CasaElectricidadApplication {

	public static void main(String[] args) {
		SpringApplication.run(CasaElectricidadApplication.class, args);
		PasswordEncoder encoder = new BCryptPasswordEncoder();
		String rawPassword = "admin123/*/";
		String hashedPassword = encoder.encode(rawPassword);

		System.out.println("[SECURITY] New Hash: " + hashedPassword);
		System.out.println("[SECURITY] Matches? " + encoder.matches(rawPassword, hashedPassword));
	}

}
