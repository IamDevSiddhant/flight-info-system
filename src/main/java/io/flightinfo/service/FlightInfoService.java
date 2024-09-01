package io.flightinfo.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.flightinfo.entity.FlightInfoEntity;

import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public interface FlightInfoService {
    FlightInfoEntity saveFlightInfo(FlightInfoEntity info, LocalDate date) throws JsonProcessingException;
}
