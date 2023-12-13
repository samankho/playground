package dev.saman.playground.repository;

import java.util.UUID;

import org.springframework.data.neo4j.repository.Neo4jRepository;

import dev.saman.playground.model.Group;

public interface GroupRepository extends Neo4jRepository<Group, UUID> {

	Group findByName(String name);

}
