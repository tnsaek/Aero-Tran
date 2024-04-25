package edu.miu.cs489.aerotran.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AirportDto {
    private Long airportId;
    private  String name;
//    private List<FlightDto> flights;
    private AddressDto address;
}
