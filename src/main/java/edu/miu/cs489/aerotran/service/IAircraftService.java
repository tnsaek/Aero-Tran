package edu.miu.cs489.aerotran.service;

import edu.miu.cs489.aerotran.dto.AircraftDto;
import org.springframework.data.domain.Page;
import java.util.List;

public interface IAircraftService {
    public AircraftDto addAircraft(AircraftDto aircraftDto);
    public Page<AircraftDto> getAllAircraftsPaged(int pageNum);
    public AircraftDto getAircraft(Long aircraftId);
    public List<AircraftDto> getAllAircrafts();
    public void deleteAircraftById(Long aircraftId);
}
