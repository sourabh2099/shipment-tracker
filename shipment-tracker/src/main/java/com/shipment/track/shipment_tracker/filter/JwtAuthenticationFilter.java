package com.shipment.track.shipment_tracker.filter;

import com.shipment.track.shipment_tracker.service.AuthService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;
import java.util.Objects;
@Slf4j
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    @Autowired
    HandlerExceptionResolver handlerExceptionResolver;
    @Autowired
    private AuthService authService;
    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization");
        if(Objects.isNull(authHeader) ||  !authHeader.startsWith("Bearer ")){
            filterChain.doFilter(request,response);
            return;
        }
        final String jwt = authHeader.substring(7);
        final String userName = authService.extractUser(jwt);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        try{
            if(userName != null && authentication == null){ // this means that the authentication object was not initialized before
                UserDetails userDetails = userDetailsService.loadUserByUsername(userName);
                log.info("Filter : {}",userDetails.getAuthorities());
                if(authService.isTokenValid(jwt)){
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            userDetails.getAuthorities());
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }
            log.info("Is Authenticated ? {}", SecurityContextHolder.getContext().getAuthentication().isAuthenticated());
            filterChain.doFilter(request,response);
        }catch (Exception e){
            log.error("Found error in JwtAuthentication filter ",e);
            handlerExceptionResolver.resolveException(request,
                    response,
                    null,e);
        }
    }

}
