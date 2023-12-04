package dev.saman.playground.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.saman.playground.model.User;

@RestController
@RequestMapping(path = "/api/users")
public class UserController {
	@GetMapping("/")
	public String hello() {
		return "Hello World";
	}

	@PostMapping("/register")
	public User register() {
		return null;
	}
}
