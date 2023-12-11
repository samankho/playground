package dev.saman.playground.repository;

import java.util.UUID;

import org.springframework.data.repository.CrudRepository;

import dev.saman.playground.model.User;

public interface UserRepository extends CrudRepository<User, UUID> {

	User findByEmail(String email);

	User findByName(String name);

}
