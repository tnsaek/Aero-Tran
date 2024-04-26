package edu.miu.cs489.aerotran.service.impl;

import edu.miu.cs489.aerotran.dto.AircraftDto;
import edu.miu.cs489.aerotran.entity.Aircraft;
import edu.miu.cs489.aerotran.repository.AircraftRepository;
import edu.miu.cs489.aerotran.service.IAircraftService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AircraftServiceImpl implements IAircraftService {

    private final AircraftRepository aircraftRepository;
    private final ModelMapper mapper;

    @Override
    public AircraftDto addAircraft(AircraftDto aircraftDto) {
        Aircraft aircraft = mapper.map(aircraftDto, Aircraft.class);
        Aircraft savedAircraft = aircraftRepository.save(aircraft);
        return mapper.map(savedAircraft, AircraftDto.class);
    }

    @Override
    public Page<AircraftDto> getAllAircraftsPaged(int pageNum) {
        Page<Aircraft> aircraftList = aircraftRepository.findAll(PageRequest.of(pageNum, 10, Sort.by("type")));
        Page<AircraftDto> aircraftDtos = new PageImpl<>(aircraftList.stream().map(aircraft ->
                mapper.map(aircraft, AircraftDto.class)).collect(Collectors.toList()));
        return aircraftDtos;
    }

    @Override
    public AircraftDto getAircraft(Long aircraftId) {
        Assert.notNull(aircraftId, "Aircraft ID must not be null");
        Aircraft aircraft = aircraftRepository.findById(aircraftId).orElseThrow(() -> new RuntimeException("Aircraft not found"));
        AircraftDto aircraftDto = mapper.map(aircraft, AircraftDto.class);
        return aircraftDto;
    }


    @Override
    public List<AircraftDto> getAllAircrafts() {
        List<Aircraft> aircraftList = aircraftRepository.findAll();
        return aircraftList.stream().map(aircraft ->
                mapper.map(aircraft, AircraftDto.class)).collect(Collectors.toList());
    }

    @Override
    public void deleteAircraftById(Long aircraftId) {
        aircraftRepository.deleteById(aircraftId);
    }
}
