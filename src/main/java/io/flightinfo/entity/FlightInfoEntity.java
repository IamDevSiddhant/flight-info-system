package io.flightinfo.entity;


import com.fasterxml.jackson.annotation.JsonFormat;
import io.flightinfo.model.FlightDetailsModel;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Data
@Table(name = "Flight_Details")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FlightInfoEntity {

    @Id
    private long flightId;
    private String airLine;
    private String departureCity;
    private String arrivalCity;
    @JsonFormat(pattern = "MM-dd-yyyy",shape = JsonFormat.Shape.STRING)
    private LocalDate availableDate;
    @JsonFormat(pattern = "MM-dd-yyyy' 'HH:mm:ss' '",timezone = "GMT",shape = JsonFormat.Shape.STRING)
    private LocalDateTime departureTime;
    @JsonFormat(pattern = "MM-dd-yyyy' 'HH:mm:ss' '",timezone = "GMT",shape = JsonFormat.Shape.STRING)
    private LocalDateTime arrivalTime;
    private long ticketPrice;


    public FlightInfoEntity(String airLine,String departureCity,String arrivalCity,LocalDate availableDate,long ticketPrice){
        this.airLine=airLine;
        this.departureCity=departureCity;
        this.arrivalCity=arrivalCity;
        this.availableDate=availableDate;
        this.departureTime=departTime(this.availableDate);
        this.arrivalTime=arrivalTime(this.availableDate);
        this.ticketPrice=ticketPrice;
    }

    public LocalDateTime departTime(LocalDate availableDate){
        return LocalDateTime.of(availableDate,LocalTime.now(ZoneOffset.UTC).plusHours(5));
    }

    public LocalDateTime arrivalTime(LocalDate availableDate){
        return LocalDateTime.of(availableDate,LocalTime.now(ZoneOffset.UTC).plusHours(2));
    }




}
