package edu.miu.cs489.aerotran.repository;

import edu.miu.cs489.aerotran.entity.Aircraft;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AircraftRepository extends JpaRepository<Aircraft, Long> {
}
