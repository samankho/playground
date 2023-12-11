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

import dev.saman.playground.model.Group;
import dev.saman.playground.service.GroupService;

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

	@GetMapping("/{id}")
	public Group getGroupById(@PathVariable UUID id) {
		Group group = groupService.get(id);
		if (group == null)
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);

		return group;
	}

	@GetMapping("/{name}")
	public Group getGroupByName(@PathVariable String name) {
		Group group = groupService.getByName(name);
		if (group == null)
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);

		return group;
	}

	@DeleteMapping("/{id}")
	public void deleteGroup(@PathVariable UUID id) {
		groupService.remove(id);
	}

	@PostMapping("/create")
	public Group createGroup(@RequestBody Group group) {
		return groupService.save(group.getName(), group.getTitle(), group.getDescription());
	}

}
