package dev.saman.playground.service;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.stereotype.Service;

import dev.saman.playground.model.Group;
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

	public void remove(UUID id) {
		groupRepository.deleteById(id);
	}

	public Group save(String name, String title, String description) {
		Group group = new Group();
		group.setName(name);
		group.setTitle(title);
		group.setDescription(description);
		LocalDateTime currentDateTime = LocalDateTime.now();
		group.setCreated_at(currentDateTime);
		groupRepository.save(group);

		return group;
	}

}
