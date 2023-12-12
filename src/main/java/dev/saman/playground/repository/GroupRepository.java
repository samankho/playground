package dev.saman.playground.repository;

import java.util.UUID;

import org.springframework.data.repository.CrudRepository;

import dev.saman.playground.model.Group;

public interface GroupRepository extends CrudRepository<Group, UUID> {

	Group findByName(String name);

}
