package io.flightinfo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;


@Data
@NoArgsConstructor
@Component
public class FlightDetailsModel{
    private String airLine;
    private String departureCity;
    private String arrivalCity;
}
