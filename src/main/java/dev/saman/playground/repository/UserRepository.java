package dev.saman.playground.repository;

import java.util.UUID;

import org.springframework.data.neo4j.repository.Neo4jRepository;

import dev.saman.playground.model.User;

public interface UserRepository extends Neo4jRepository<User, UUID> {

	User findByEmail(String email);

	User findByName(String name);

}
