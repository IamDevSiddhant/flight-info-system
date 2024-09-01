package io.flightinfo.config;

import io.flightinfo.entity.UserDetailsModel;

import io.flightinfo.repository.FlightInfoUsersRepo;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import java.util.Optional;

@Component
@Slf4j
//@NoArgsConstructor
public class UserInfoUserDetailsService implements UserDetailsService {
    @Autowired
    private FlightInfoUsersRepo usersRepo;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("user name "+username);
       Optional<UserDetailsModel>user = usersRepo.findByUserName(username);
        log.info("user {}",user);
       return user.map(UserInfoUserDetails::new)
                .orElseThrow(()->new RuntimeException("user not found::"+username));
    }
}
