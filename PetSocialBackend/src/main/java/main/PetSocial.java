package main;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import repo.AccountDataRepo;
import rest.controller.AccountController;
import security.AuthenticationFilter;
import security.config.SpringSecurityConfig;
import service.AccountService;

@SpringBootApplication
@EnableMongoRepositories(basePackageClasses = {AccountDataRepo.class})
@ComponentScan(basePackageClasses = {AccountService.class, SpringSecurityConfig.class, AuthenticationFilter.class, AccountController.class, PetSocial.class})
@EnableAutoConfiguration
public class PetSocial implements CommandLineRunner{
	public static void main(String[] args) {
		SpringApplication.run(PetSocial.class, args);
	}
	
	@Override
	public void run(String... args) throws Exception {
	}
	
	@Bean
	public BCryptPasswordEncoder encoder() {
		return new BCryptPasswordEncoder();
	}
	
}
