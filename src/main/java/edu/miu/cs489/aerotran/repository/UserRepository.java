package edu.miu.cs489.aerotran.repository;

import edu.miu.cs489.aerotran.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
