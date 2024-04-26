package edu.miu.cs489.aerotran.service.impl;

import edu.miu.cs489.aerotran.dto.PassengerDto;
import edu.miu.cs489.aerotran.entity.Passenger;
import edu.miu.cs489.aerotran.repository.PassengerRepository;
import edu.miu.cs489.aerotran.service.IPassengerService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PassengerServiceImpl implements IPassengerService {

    private final PassengerRepository passengerRepository;
    private final ModelMapper mapper;

    @Override
    public PassengerDto addPassenger(PassengerDto passengerDto) {
        Passenger passenger = mapper.map(passengerDto, Passenger.class);
        Passenger savedPassenger = passengerRepository.save(passenger);
        return mapper.map(savedPassenger, PassengerDto.class);
    }

    @Override
    public List<PassengerDto> getAllPassengers() {
        List<Passenger> passengers = passengerRepository.findAll();
        return passengers.stream().map(passenger ->
                mapper.map(passenger, PassengerDto.class)).collect(Collectors.toList());
    }

    @Override
    public PassengerDto getPassenger(Long passengerId) {
        Passenger passenger = passengerRepository.findById(passengerId).orElse(null);
        return mapper.map(passenger, PassengerDto.class);
    }

    @Override
    public void deletePassenger(Long passengerId) {

    }
}
