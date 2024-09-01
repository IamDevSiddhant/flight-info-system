package io.flightinfo.filter;


import io.flightinfo.config.UserInfoUserDetailsService;
import io.flightinfo.serviceImpl.JWTService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@Slf4j
public class JWTFilter extends OncePerRequestFilter {


    private final JWTService jwtService;
    private final UserInfoUserDetailsService userInfoUserDetailsService;


    public JWTFilter(JWTService jwtService, UserInfoUserDetailsService userInfoUserDetailsService) {
        this.jwtService = jwtService;
        this.userInfoUserDetailsService = userInfoUserDetailsService;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        String token = null;
        String userName = null;
        log.info("in JWTFilter->doFilterInternal");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            log.info("authHeader is not null");
            token = authHeader.substring(7);
            userName = jwtService.extractUsername(token);
        }

        if (userName != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            log.info("JWTFilter doFilterInternal");
            UserDetails details = userInfoUserDetailsService.loadUserByUsername(userName);
            if (jwtService.validateToken(token, details)) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(details, null, details.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }

        }
        filterChain.doFilter(request, response);

    }
}
