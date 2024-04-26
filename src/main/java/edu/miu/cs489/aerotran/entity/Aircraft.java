package edu.miu.cs489.aerotran.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Aircraft {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long aircraftId;
    private String type;
    private Integer seats;
    @OneToMany(mappedBy = "aircraft", cascade =  CascadeType.ALL )
    private List<Flight> flights = new ArrayList<Flight>();

}
