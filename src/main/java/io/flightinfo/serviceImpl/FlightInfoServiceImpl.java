package io.flightinfo.serviceImpl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.flightinfo.entity.FlightInfoEntity;
import io.flightinfo.repository.FlightInfoRepo;
import io.flightinfo.service.FlightInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Random;

@Component
@Slf4j
public class FlightInfoServiceImpl implements FlightInfoService {


    private final FlightInfoRepo repo;
    private final ObjectMapper mapper;

    public FlightInfoServiceImpl(FlightInfoRepo repo, ObjectMapper mapper) {
        this.repo = repo;
        this.mapper = mapper;
    }

    @Override
    public FlightInfoEntity saveFlightInfo(FlightInfoEntity info, LocalDate date) throws JsonProcessingException {
        long id=0L;
        Random random = new Random();
        id = random.nextInt(900000)+100000;

        FlightInfoEntity build = new FlightInfoEntity(info.getAirLine(),info.getDepartureCity(),info.getArrivalCity()
                ,date,info.getTicketPrice());
        build.setFlightId(id);
        //build.setFlightDetailsModelList(info.getFlightDetailsModelList());
        log.info(mapper.writeValueAsString(build));
        repo.save(build);
        return build;
    }
}
