package org.generation.italy.demo;

import java.util.HashSet;
import java.util.Set;

import org.generation.italy.demo.pojo.Role;
import org.generation.italy.demo.pojo.User;
import org.generation.italy.demo.service.RoleService;
import org.generation.italy.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringSecurityApplication implements CommandLineRunner {
	
	@Autowired
	private RoleService roleService;
	
	@Autowired
	private UserService userService;

	public static void main(String[] args) {
		SpringApplication.run(SpringSecurityApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		//Mi creo i ruoli
		Role roleUser = new Role("USER");
		Role roleAdmin = new Role("ADMIN");
		
		//Salvo i ruoli
		roleService.save(roleUser);
		roleService.save(roleAdmin);
		
		//Creo gli utenti
		User user = new User("user", "{noop}userpsw", roleUser);
		User admin = new User("admin", "{noop}adminpsw", roleAdmin);
		
		//Creo la lista di ruoli
		Set<Role> roleAdminUser = new HashSet<>();
		
		//Aggiungo i ruoli alla lista
		roleAdminUser.add(roleUser);
		roleAdminUser.add(roleAdmin);
		
		User userAdmin = new User ("userAdmin", "{noop}useradminpsw", roleAdminUser);
		
		userService.save(user);
		userService.save(admin);
		userService.save(userAdmin);
	}

}
