package dev.saman.playground.model;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import jakarta.annotation.Nonnull;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;

@Entity
public class User {

	@Id
	@GeneratedValue
	private UUID id;

	private String name;

	@Column(unique = true)
	@Nonnull
	private String email;

	@Nonnull
	private String password;

	@ManyToMany
	@JoinTable(name = "user_group", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "group_id"))
	private Set<Group> groups;

	@OneToMany(mappedBy = "admin")
	private Set<Group> adminOfGroups;

	public User() {
		adminOfGroups = new HashSet<>();
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Group getGroup() {
		return group;
	}

	public void setGroup(Group group) {
		this.group = group;
	}

	public Set<Group> getAdminOfGroups() {
		return adminOfGroups;
	}

	public void setAdminOfGroups(Set<Group> adminOfGroups) {
		this.adminOfGroups = adminOfGroups;
	}

}
