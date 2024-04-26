package edu.miu.cs489.aerotran.repository;

import edu.miu.cs489.aerotran.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
