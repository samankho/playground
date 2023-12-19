package dev.saman.playground.web;

import java.security.Principal;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/auth")
public class AuthController {

	@GetMapping("/hello")
	public String hello(Principal principal) {
		return "Hello, you're authenticated as " + principal.getName();
	}

	@PostMapping("/login")
	public ResponseEntity<?> login() {

		return null;
	}

	@PostMapping("/logout")
	public ResponseEntity<?> logout() {

		return null;
	}
}
