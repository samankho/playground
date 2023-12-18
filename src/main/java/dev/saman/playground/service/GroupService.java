package dev.saman.playground.service;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.stereotype.Service;

import dev.saman.playground.model.Group;
import dev.saman.playground.model.User;
import dev.saman.playground.repository.GroupRepository;

@Service
public class GroupService {

	private final GroupRepository groupRepository;

	public GroupService(GroupRepository groupRepository) {
		this.groupRepository = groupRepository;
	}

	public Iterable<Group> get() {
		return groupRepository.findAll();
	}

	public Group get(UUID id) {
		return groupRepository.findById(id).orElse(null);
	}

	public Group getByName(String name) {
		return groupRepository.findByName(name);
	}

	public void deleteById(UUID id) {
		groupRepository.deleteById(id);
	}

	public Group create(String name, String title, String description) {
		Group group = new Group();
		group.setName(name);
		group.setTitle(title);
		group.setDescription(description);
		group.setCreated_at(LocalDateTime.now());
		group.setUpdated_at(null);
		groupRepository.save(group);

		return group;
	}

	public Group update(UUID id, Group group) {
		Group result = groupRepository.findById(id).orElse(null);
		result.setTitle(group.getTitle());
		result.setDescription(group.getDescription());
		result.setUpdated_at(LocalDateTime.now());
		groupRepository.save(result);

		return result;
	}

	public void setAdmin(User user, Group group) {
		group.setAdmin(user);
	}

}
