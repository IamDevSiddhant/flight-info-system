package io.flightinfo.repository;

import io.flightinfo.entity.UserDetailsModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface FlightInfoUsersRepo extends JpaRepository<UserDetailsModel,String> {
    Optional<UserDetailsModel> findByUserName(String userName);
    Optional<UserDetailsModel> findByUserEmail(String userEmail);
}
