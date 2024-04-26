package edu.miu.cs489.aerotran.repository;

import edu.miu.cs489.aerotran.entity.Passenger;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PassengerRepository extends JpaRepository<Passenger, Long> {
}
