package io.flightinfo.config;


import io.flightinfo.filter.JWTFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {


    @Autowired
    private JWTFilter filter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity security) throws Exception {
        return security
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorizationManagerRequestMatcherRegistry -> {
                    authorizationManagerRequestMatcherRegistry
                            .requestMatchers("/api/v1/interactions/flightinfo/token","/api/v1/interactions/flightinfo/test","/api/v1/interactions/flightinfo/saveUser/*","/eureka/**").permitAll()
                            .requestMatchers("/api/v1/interactions/flightinfo/save")
                            .authenticated();
                }).sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authProvider())
                .addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class)
                .build();

    }

    @Bean
    public UserDetailsService userDetailsService(){
        return new UserInfoUserDetailsService();
    }


    @Bean
    public PasswordEncoder encoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authProvider(){
            DaoAuthenticationProvider authenticationProvider=new DaoAuthenticationProvider();
            authenticationProvider.setUserDetailsService(userDetailsService());
            authenticationProvider.setPasswordEncoder(encoder());
            return authenticationProvider;
    }

    @Bean
    public AuthenticationManager authManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }





}
