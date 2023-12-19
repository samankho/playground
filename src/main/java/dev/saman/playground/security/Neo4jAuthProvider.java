package dev.saman.playground.security;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.neo4j.driver.Driver;
import org.neo4j.driver.Record;
import org.neo4j.driver.Session;
import org.neo4j.driver.types.Node;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import dev.saman.playground.repository.UserRepository;

@Component
public class Neo4jAuthProvider implements AuthenticationProvider {
	private final Driver driver;
	private final UserRepository userRepository;

	public Neo4jAuthProvider(Driver driver, UserRepository userRepository) {
		this.driver = driver;
		this.userRepository = userRepository;
	}

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String email = authentication.getName();
		String password = authentication.getCredentials().toString();
		dev.saman.playground.model.User actualUser = userRepository.findByEmail(email);
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		if (!encoder.matches(password, actualUser.getPassword())) {
			return null;
		}
		try (Session session = driver.session()) {
			List<Record> results = session.run("MATCH (n:User) WHERE n.email = $email RETURN n", Map.of("email", email))
					.list();

			if (results.isEmpty()) {
				return null;
			}

			Node user = results.get(0).get("n").asNode();
			// Possible to add more information from user
			List<GrantedAuthority> authorities = new ArrayList<>();
			authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
			final UserDetails principal = new User(email, password, authorities);

			return new UsernamePasswordAuthenticationToken(principal, password, authorities);
		}
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}
}
