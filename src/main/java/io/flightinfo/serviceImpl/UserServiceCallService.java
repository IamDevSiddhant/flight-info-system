package io.flightinfo.serviceImpl;

import io.flightinfo.entity.UserDetailsModel;
import io.flightinfo.repository.FlightInfoUsersRepo;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
@Slf4j
public class UserServiceCallService {


    private final RestTemplate restTemplate;
    private final FlightInfoUsersRepo usersRepo;

    public UserServiceCallService(RestTemplate restTemplate, FlightInfoUsersRepo usersRepo) {
        this.restTemplate = restTemplate;
        this.usersRepo = usersRepo;
    }


    public UserDetailsModel getUserFromAnotherService(String userEmail,String URL){

        ResponseEntity<UserDetailsModel> exchange = null;

        Map<String, String> uriVariables = new HashMap<>();
        uriVariables.put("userEmail", userEmail);
        HttpHeaders headers = new HttpHeaders();
        headers.add("content", MediaType.APPLICATION_JSON_VALUE);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        exchange = restTemplate.exchange(URL, HttpMethod.GET, entity, UserDetailsModel.class, uriVariables);
        log.info("user saved {} ", exchange.getBody());
        usersRepo.save(Objects.requireNonNull(exchange.getBody()));
        return exchange.getBody();
    }
}
