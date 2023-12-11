package dev.saman.playground.web;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import dev.saman.playground.model.User;
import dev.saman.playground.service.UserService;

@RestController
@RequestMapping(path = "/api/users")
public class UserController {

	private final UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}

	@GetMapping("/")
	public Iterable<User> getAllUser() {
		return userService.get();
	}

	@GetMapping("/id/{id}")
	public User getUserById(@PathVariable UUID id) {
		User user = userService.get(id);
		if (user == null)
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);

		return user;
	}

	@GetMapping("/mail/{email}")
	public User getUserByEmail(@PathVariable String email) {
		User user = userService.getByEmail(email);
		if (user == null)
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);

		return user;
	}

	@GetMapping("/name/{name}")
	public User getUserByName(@PathVariable String name) {
		User user = userService.getByName(name);
		if (user == null)
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);

		return user;
	}

	@DeleteMapping("/id/{id}")
	public void deleteUser(@PathVariable UUID id) {
		userService.remove(id);
	}

	@PostMapping("/register")
	public User createUser(@RequestBody User user) {
		return userService.save(user.getEmail(), user.getPassword(), user.getName());
	}

}
