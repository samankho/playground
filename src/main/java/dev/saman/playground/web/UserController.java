package dev.saman.playground.web;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import dev.saman.playground.model.User;
import dev.saman.playground.service.UserService;
import jakarta.validation.Valid;

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
	public ResponseEntity<User> getUserById(@PathVariable UUID id) {
		User result = userService.get(id);
		if (result == null)
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);

		return ResponseEntity.ok().body(result);
	}

	@GetMapping("/email/{email}")
	public ResponseEntity<User> getUserByEmail(@PathVariable String email) {
		User result = userService.getByEmail(email);
		if (result == null)
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);

		return ResponseEntity.ok().body(result);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteUser(@PathVariable UUID id) {
		User user = userService.get(id);
		if (user == null)
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		userService.deleteById(id);

		return ResponseEntity.ok().build();
	}

	@PostMapping("/register")
	public ResponseEntity<User> createUser(@Valid @RequestBody User user) throws URISyntaxException {
		if (userService.getByEmail(user.getEmail()) != null)
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);

		User result = userService.create(user.getEmail(), user.getPassword(), user.getName());

		return ResponseEntity.created(new URI("/api/users/id/" + result.getId())).body(result);
	}

	@PutMapping("/{id}")
	public ResponseEntity<User> updateUser(@PathVariable UUID id, @Valid @RequestBody User user) {
		User old_user = userService.get(id);
		if (old_user == null)
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);

		int to_update = 0;
		if (!old_user.getName().equals(user.getName())) {
			to_update++;
		}
		if (to_update == 0) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		}

		userService.update(id, user);

		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

	}

}
