package edu.miu.cs489.aerotran.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
    @NotBlank
    private String type;
    @NotNull
    private Integer seats;
    @OneToMany(mappedBy = "aircraft", cascade =  CascadeType.ALL )
    private List<Flight> flights = new ArrayList<Flight>();

}
