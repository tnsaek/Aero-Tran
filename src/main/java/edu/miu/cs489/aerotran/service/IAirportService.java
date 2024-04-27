package edu.miu.cs489.aerotran.service;

import edu.miu.cs489.aerotran.dto.AirportDto;
import org.springframework.data.domain.Page;
import java.util.List;

public interface IAirportService {

    public AirportDto addAirport(AirportDto airportDto);
    public Page<AirportDto> getAllAirportPaged(int pageNum);
    public List<AirportDto> getAllAirports();
    public AirportDto getAirport(Long airportId);
    public void deleteAirport(Long airportId);

}
