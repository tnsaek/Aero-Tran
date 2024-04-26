package edu.miu.cs489.aerotran.repository;

import edu.miu.cs489.aerotran.entity.Airport;
import edu.miu.cs489.aerotran.entity.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface FlightRepository extends JpaRepository<Flight, Long> {

    @Query("SELECT f from Flight f where f.departureAirport.name=:departureAirport and f.arrivalAirport.name=:arrivalAirport")
    Optional<List<Flight>> findFlightByDepartureCityAndArrivalCity(String departureAirport, String arrivalAirport);

    @Query("SELECT f from  Flight f where f.departureDate=:departureDate and f.departureAirport=:departureAirport " +
            "and f.arrivalAirport=:arrivalAirport")
    Optional<List<Flight>> findFlightByDepartureDateAndDepartureCityAndArrivalCity(LocalDate departureDate,
                                                                                   Airport departureAirport, Airport arrivalAirport);
}
