package io.flightinfo.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.flightinfo.entity.FlightInfoEntity;
import io.flightinfo.entity.UserDetailsModel;
import io.flightinfo.model.AuthRequest;
import io.flightinfo.repository.FlightInfoUsersRepo;
import io.flightinfo.service.FlightInfoService;
//import io.flightinfo.serviceImpl.JWTService;
import io.flightinfo.serviceImpl.JWTService;
import io.flightinfo.serviceImpl.UserServiceCallService;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.*;


@RestController
@RequestMapping("/api/v1/interactions/flightinfo")
@Slf4j
public class FlightInfoController {

    private final FlightInfoService service;
    private final AuthenticationManager manager;
    private final JWTService jwtService;
    private final RestTemplate restTemplate;

    private final FlightInfoUsersRepo usersRepo;

    private final List<String> emailList;


    private final String URL;

    private final UserServiceCallService userServiceCallService;
    //private final HttpServletRequest request;

    public FlightInfoController(FlightInfoService service, AuthenticationManager manager, JWTService jwtService, RestTemplate restTemplate, FlightInfoUsersRepo usersRepo, @Value("${entity.user.email}") List<String> emailList, @Value("${entity.flightuser.url}") String url, UserServiceCallService userServiceCallService) {
        this.service = service;
        this.manager = manager;
        this.jwtService = jwtService;
        this.restTemplate = restTemplate;
        this.usersRepo = usersRepo;
        this.emailList = emailList;
        this.URL = url;
        this.userServiceCallService = userServiceCallService;
        //this.request = request;
    }


    @PostMapping("/save")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<?> saveFlightInfo(@RequestBody FlightInfoEntity entity) throws JsonProcessingException {
        return new ResponseEntity<>(service.saveFlightInfo(entity, entity.getAvailableDate()), HttpStatus.CREATED);
    }

    @PostMapping("/token")
    public ResponseEntity<?> createToken(@RequestBody AuthRequest authRequest) {
        String authToken = "";
        log.info("creating token");

        Authentication authentication = manager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));

        if (authentication.isAuthenticated()) {
            log.info("in if block, token creation");
            authToken = jwtService.generateToken(authRequest.getUsername());
        } else {
            throw new UsernameNotFoundException("User not found");
        }
        log.info("token {}", authToken);
        Map<String, Object> authTokenMap = new HashMap<>();
        authTokenMap.put("token", authToken);
        authTokenMap.put("authType","bearer");
        authTokenMap.put("expiration time",jwtService.extractExpirationTime(authToken));

        return new ResponseEntity<>(authTokenMap, HttpStatus.OK);
    }

    @GetMapping("/saveUser/{userEmail}")
    public UserDetailsModel getUserDetailsByName(@PathVariable("userEmail") String userEmail) {

        log.info("getUserDetailsByName {}", userEmail);
        log.info("emailList {} ", emailList);
        log.info("emailList.contains(userEmail) {}",emailList.contains(userEmail));
        Optional<UserDetailsModel> byUserEmail = usersRepo.findByUserEmail(userEmail);
        log.info("byUserEmail {}", byUserEmail);
        ResponseEntity<UserDetailsModel> exchange = null;

        if (emailList.contains(userEmail)) {
            log.info("userEmail valid");
            if (byUserEmail.isEmpty()) {
                log.info("making ser call");
                userServiceCallService.getUserFromAnotherService(userEmail,URL);
            }
            return byUserEmail.get();
        } else {
            throw new RuntimeException("email is invalid");
        }

    }

    private String getTokenLink(HttpServletRequest servlet){
        return "http://"+servlet.getServerName()
                +":"
                +servlet.getServerPort()
                +servlet.getContextPath();
    }



}
