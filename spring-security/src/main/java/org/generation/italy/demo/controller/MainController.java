package org.generation.italy.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class MainController {

	@GetMapping
	public String getHome() {
		
		return "Home";
	}
	
	@GetMapping("/user")
	public String getUser() {
		
		return "User";
	}
	
	@GetMapping("/admin")
	public String getAdmin() {
		
		return "Admin";
	}
	
	@GetMapping("/userAdmin")
	public String getUserAdmin() {
		
		return "UserAdmin";
	}
}
