package io.flightinfo.repository;

import io.flightinfo.entity.FlightInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

@Repository
public interface FlightInfoRepo extends JpaRepository<FlightInfoEntity,Long> {

}
