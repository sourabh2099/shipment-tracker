package com.shipment.track.shipment_tracker.sources;

import com.shipment.track.shipment_tracker.model.User;
import com.shipment.track.shipment_tracker.service.AuthService;
import com.shipment.track.shipment_tracker.service.CrudOperations;
import com.shipment.track.shipment_tracker_pojo.pojo.CreateUserDto;
import com.shipment.track.shipment_tracker_pojo.pojo.LoginUserDto;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

import static com.shipment.track.shipment_tracker.utils.AppUtils.respondWithError;

@RestController()
@RequestMapping(path = "/auth")
public class AuthController {
    private static Logger LOG = LoggerFactory.getLogger(AuthController.class);
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private AuthService authService;

    @Autowired
    private CrudOperations crudOperations;

    @PostMapping("/test")
    public ResponseEntity<?> testController(@RequestBody LoginUserDto loginUserDto) {
        LOG.info("Hi password: {} , userName: {}", loginUserDto.getPassword(), loginUserDto.getEmail());
        Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginUserDto.getEmail(), loginUserDto.getPassword()));
        User user = (User) authenticate.getPrincipal();
        String token = authService.buildToken(Collections.emptyMap(), user);
        return ResponseEntity.ok(token);
    }

    @PostMapping("/create-user")
    public ResponseEntity<?> createUser(@Valid @RequestBody CreateUserDto createUserDto, BindingResult bindingResult) {
        LOG.info("Got Request as {}", createUserDto);
        if (bindingResult.hasErrors()) {
            return respondWithError(bindingResult, "/v1/create-user");
        }
        User user = crudOperations.createUser(createUserDto);
        //event triggered to create shipments for the given user
        //applicationEventPublisher.publishEvent(new CreateShipmentsForUserEvent(this, user));
        return ResponseEntity.ok(user);
    }


}
