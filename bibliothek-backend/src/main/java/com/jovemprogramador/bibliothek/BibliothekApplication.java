package com.jovemprogramador.bibliothek;

import com.jovemprogramador.bibliothek.model.User;
import com.jovemprogramador.bibliothek.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class BibliothekApplication {

	public static void main(String[] args) {
		SpringApplication.run(BibliothekApplication.class, args);
	}

	/*@Bean
	CommandLineRunner commandLineRunner(UserRepository users, PasswordEncoder encoder) {
		return args -> {
			users.save(new User("0","Administrador",encoder.encode("password"),"ROLE_USER,ROLE_ADMIN", ""));
		};
	}
*/
}
