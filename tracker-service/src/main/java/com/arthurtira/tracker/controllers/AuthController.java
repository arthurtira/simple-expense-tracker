package com.arthurtira.tracker.controllers;

import com.arthurtira.tracker.dto.AuthRequestDto;
import com.arthurtira.tracker.dto.AuthResponseDto;
import com.arthurtira.tracker.dto.UserEntityDto;
import com.arthurtira.tracker.exceptions.UserAlreadyExistsException;
import com.arthurtira.tracker.services.AuthService;

import com.arthurtira.tracker.services.UserEntityService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/auth")
@Slf4j
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final AuthService authService;
    private final UserEntityService userEntityService;

    public AuthController(AuthenticationManager authenticationManager, AuthService authService, UserEntityService userEntityService) {
        this.authenticationManager = authenticationManager;
        this.authService = authService;
        this.userEntityService = userEntityService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequestDto requestDto) {
        try{
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(requestDto.getUsername(), requestDto.getPassword()));
        }catch (BadCredentialsException e){
            throw new RuntimeException("Username or password incorrect");
        }

        String jwt = authService.login(requestDto.getUsername());
        return new ResponseEntity<>(AuthResponseDto.builder().jwt(jwt).build() , HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<?> createUser(@RequestBody UserEntityDto userEntityDtoRequest) throws UserAlreadyExistsException {
        log.debug("Create user {} ");
        return new ResponseEntity<>(this.userEntityService.createAccount(userEntityDtoRequest), HttpStatus.OK);
    }


}
