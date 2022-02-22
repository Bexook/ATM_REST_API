package com.example.atm.rest;

import com.example.atm.config.security.service.JWTTokenService;
import com.example.atm.exceptions.DataNotFoundException;
import com.example.atm.model.UserCredentials;
import com.example.atm.service.JWTAuthenticationService;
import org.apache.tomcat.websocket.AuthenticationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class AuthController {

    @Autowired
    private JWTAuthenticationService jwtAuthenticationService;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserCredentials creds) throws AuthenticationException {
        return ResponseEntity.ok(jwtAuthenticationService.login(creds));
    }

    @PostMapping("/logout")
    public void logout(HttpServletRequest httpServletRequest) throws AuthenticationException, DataNotFoundException {
        jwtAuthenticationService.logout(httpServletRequest);
    }


}
