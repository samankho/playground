package dev.saman.playground.service;

import java.util.UUID;

import org.springframework.stereotype.Service;

import dev.saman.playground.model.User;
import dev.saman.playground.repository.UserRepository;

@Service
public class UserService {

	private final UserRepository userRepository;

	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public Iterable<User> get() {
		return userRepository.findAll();
	}

	public User get(UUID id) {
		return userRepository.findById(id).orElse(null);
	}

	public User getByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	public User getByName(String name) {
		return userRepository.findByName(name);
	}

	public void remove(UUID id) {
		userRepository.deleteById(id);
	}

	public User save(String email, String password, String name) {
		User user = new User();
		user.setName(name);
		user.setEmail(email);
		user.setPassword(password);
		userRepository.save(user);

		return user;
	}

}
