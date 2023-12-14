package dev.saman.playground.service;

import java.time.LocalDateTime;
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

	public void deleteById(UUID id) {
		userRepository.deleteById(id);
	}

	public User create(String email, String password, String name) {
		User user = new User();
		user.setName(name);
		user.setEmail(email);
		user.setPassword(password);
		user.setCreated_at(LocalDateTime.now());
		user.setUpdated_at(null);
		userRepository.save(user);

		return user;
	}

	public User update(UUID id, User user) {
		User result = userRepository.findById(id).orElse(null);
		result.setName(user.getName());
		result.setUpdated_at(LocalDateTime.now());
		userRepository.save(result);

		return result;
	}
}
