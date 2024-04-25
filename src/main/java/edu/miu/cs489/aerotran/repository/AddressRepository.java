package edu.miu.cs489.aerotran.repository;

import edu.miu.cs489.aerotran.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {
}
