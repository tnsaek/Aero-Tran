package edu.miu.cs489.aerotran.service;

import edu.miu.cs489.aerotran.dto.PassengerDto;
import edu.miu.cs489.aerotran.entity.Passenger;
import java.util.List;

public interface IPassengerService {
    public PassengerDto addPassenger(PassengerDto passengerDto);
    public List<PassengerDto> getAllPassengers();
    public Passenger getPassenger(Long passengerId);
    public void deletePassenger(Long passengerId);
}
