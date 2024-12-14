package com.shipment.track.shipment_tracker.sources;

import com.shipment.track.shipment_tracker.model.User;
import com.shipment.track.shipment_tracker.pojo.CreateUserDto;
import com.shipment.track.shipment_tracker.pojo.LoginUserDto;
import com.shipment.track.shipment_tracker.service.AuthService;
import com.shipment.track.shipment_tracker.service.CrudOperations;
import com.shipment.track.shipment_tracker.service.SearchOperations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collections;

@RestController()
@RequestMapping(path = "/auth")
public class AuthController {
    private static Logger LOG = LoggerFactory.getLogger(AuthController.class);
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private AuthService authService;

    @PostMapping("/test")
    public ResponseEntity<?> testController(@RequestBody LoginUserDto loginUserDto){
        LOG.info("Hi password: {} , userName: {}",loginUserDto.getPassword(),loginUserDto.getEmail());
        Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginUserDto.getEmail(), loginUserDto.getPassword()));
        User user = (User) authenticate.getPrincipal();
        String token = authService.buildToken(Collections.emptyMap(), user);
        return ResponseEntity.ok(token);
    }



}
