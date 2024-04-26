package edu.miu.cs489.aerotran;

import edu.miu.cs489.aerotran.entity.Role;
import edu.miu.cs489.aerotran.entity.User;
import edu.miu.cs489.aerotran.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@SpringBootApplication
@RequiredArgsConstructor
public class AeroTranApplication implements CommandLineRunner {

	private final UserRepository userRepository;
	private final PasswordEncoder encoder;


	public static void main(String[] args) {
		SpringApplication.run(AeroTranApplication.class, args);
	}


	@Override
	public void run(String... args) throws Exception {

		Role role1 = new Role(null, "ADMIN");
		Role role2 = new Role(null, "USER");

		User user1 = new User("tnsae", "tn123"," tn@em", List.of(role1));
		User user2 = new User("brhan", "br123"," br@em", List.of(role2));

		user1.setPassword(encoder.encode(user1.getPassword()));
		user2.setPassword(encoder.encode(user2.getPassword()));
		userRepository.save(user1);
		userRepository.save(user2);
	}

}
