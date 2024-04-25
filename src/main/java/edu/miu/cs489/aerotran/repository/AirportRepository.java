package edu.miu.cs489.aerotran.repository;

import edu.miu.cs489.aerotran.entity.Airport;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AirportRepository extends JpaRepository<Airport, Long> {
}
