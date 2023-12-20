package dev.saman.playground.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import dev.saman.playground.model.User;
import dev.saman.playground.repository.UserRepository;

@Service
public class AuthService {

	private final UserRepository userRepository;

	private final PasswordEncoder passwordEncoder;

	public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}

	public User authByEmail(String email, String password) {
		User user = userRepository.findByEmail(email);
		if (user == null)
			return null;
		if (!passwordEncoder.matches(password, user.getPassword()))
			return null;

		return user;
	}

	public User authByName(String name, String password) {
		User user = userRepository.findByName(name);
		if (user == null)
			return null;
		if (!passwordEncoder.matches(password, user.getPassword()))
			return null;

		return user;
	}

}