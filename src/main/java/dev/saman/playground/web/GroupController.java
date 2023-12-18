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

import dev.saman.playground.model.Group;
import dev.saman.playground.model.User;
import dev.saman.playground.service.GroupService;
import jakarta.validation.Valid;

@RestController
@RequestMapping(path = "/api/groups")
public class GroupController {

	private final GroupService groupService;

	public GroupController(GroupService groupService) {
		this.groupService = groupService;
	}

	@GetMapping("/")
	public Iterable<Group> getAllGroups() {
		return groupService.get();
	}

	@GetMapping("/id/{id}")
	public ResponseEntity<Group> getGroupById(@PathVariable UUID id) {
		Group result = groupService.get(id);
		if (result == null)
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);

		return ResponseEntity.ok().body(result);
	}

	@GetMapping("/name/{name}")
	public ResponseEntity<Group> getGroupByName(@PathVariable String name) {
		Group result = groupService.getByName(name);
		if (result == null)
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);

		return ResponseEntity.ok().body(result);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Group> deleteGroup(@PathVariable UUID id) {
		Group result = groupService.get(id);
		if (result == null)
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);

		groupService.deleteById(id);

		return ResponseEntity.ok().build();
	}

	@PostMapping("/create")
	public ResponseEntity<Group> createGroup(@Valid User user, @Valid @RequestBody Group group)
			throws URISyntaxException {
		if (groupService.getByName(group.getName()) != null)
			throw new ResponseStatusException(HttpStatus.FORBIDDEN);

		Group result = groupService.create(group.getName(), group.getTitle(), group.getDescription());
		groupService.setAdmin(user, group);

		return ResponseEntity.created(new URI("api/groups/id/" + result.getId())).body(result);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Group> updateGroup(@PathVariable UUID id, @Valid @RequestBody Group group) {
		Group old_grp = groupService.get(id);
		if (old_grp == null)
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);

		int to_update = 0;
		if (!old_grp.getDescription().equals(group.getDescription())) {
			to_update++;
		}
		if (!old_grp.getTitle().equals(group.getTitle())) {
			to_update++;
		}
		if (to_update == 0) {
			return ResponseEntity.status(HttpStatus.NOT_MODIFIED).build();
		}

		groupService.update(id, group);

		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}

}
