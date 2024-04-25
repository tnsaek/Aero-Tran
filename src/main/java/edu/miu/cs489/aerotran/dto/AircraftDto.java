package edu.miu.cs489.aerotran.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AircraftDto {
    private Long aircraftId;
    private String type;
    private Integer seats;
}
