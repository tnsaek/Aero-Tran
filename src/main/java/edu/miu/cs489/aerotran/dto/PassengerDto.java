package edu.miu.cs489.aerotran.dto;

import edu.miu.cs489.aerotran.entity.Flight;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PassengerDto {
    private Long passengerId;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String passportNumber;
    private String email;
    private Flight flight;
    private AddressDto address;
}
