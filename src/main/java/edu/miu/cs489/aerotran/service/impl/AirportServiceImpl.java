package edu.miu.cs489.aerotran.service.impl;

import edu.miu.cs489.aerotran.dto.AirportDto;
import edu.miu.cs489.aerotran.entity.Airport;
import edu.miu.cs489.aerotran.repository.AirportRepository;
import edu.miu.cs489.aerotran.service.IAirportService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AirportServiceImpl implements IAirportService {

    private final AirportRepository airportRepository;
    private final ModelMapper mapper;


    @Override
    public AirportDto addAirport(AirportDto airportDto) {
        Airport airport = mapper.map(airportDto, Airport.class);
        Airport savedAirport = airportRepository.save(airport);
        return mapper.map(savedAirport, AirportDto.class);
    }

    @Override
    public Page<AirportDto> getAllAirportPaged(int pageNum) {
        Page<Airport> airports = airportRepository.findAll(PageRequest.of(pageNum, 10, Sort.by("name")));
        Page<AirportDto> airportDtos = new PageImpl<>(airports.stream().map(airport ->
                mapper.map(airport, AirportDto.class)).collect(Collectors.toList()));
        return airportDtos;
    }

    @Override
    public List<AirportDto> getAllAirports() {
        List<Airport> airports = airportRepository.findAll();
        return airports.stream().map(airport ->
                mapper.map(airport, AirportDto.class)).collect(Collectors.toList());
    }

    @Override
    public AirportDto getAirport(Long airportId) {
        Airport airport = airportRepository.findById(airportId).orElse(null);
        return mapper.map(airport, AirportDto.class);
    }

    @Override
    public void deleteAirport(Long airportId) {
        airportRepository.deleteById(airportId);
    }
}
