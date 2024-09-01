package io.flightinfo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication

public class FlightInfoSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(FlightInfoSystemApplication.class, args);
	}

}
