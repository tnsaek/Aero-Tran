package edu.miu.cs489.aerotran;

import edu.miu.cs489.aerotran.dto.RoleDto;
import edu.miu.cs489.aerotran.dto.UserDto;
import edu.miu.cs489.aerotran.service.impl.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.util.List;

@SpringBootApplication
@RequiredArgsConstructor
public class AeroTranApplication implements CommandLineRunner {

	private final UserService userService;


	public static void main(String[] args) {
		SpringApplication.run(AeroTranApplication.class, args);
	}


	@Override
	public void run(String... args) throws Exception {

//		RoleDto role1 = new RoleDto(null, "ADMIN");
//		RoleDto role2 = new RoleDto(null, "USER");
//
//		UserDto user1 = new UserDto("tnsae", "tn123","tn@em", List.of(role1));
//		UserDto user2 = new UserDto("brhan", "br123","br@em", List.of(role2));
//
//		userService.addUser(user1);
//		userService.addUser(user2);
	}

}
