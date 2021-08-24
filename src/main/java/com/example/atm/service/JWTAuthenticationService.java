package com.example.atm.service;

import com.example.atm.exceptions.DataNotFoundException;
import com.example.atm.model.UserCredentials;
import org.apache.tomcat.websocket.AuthenticationException;

import javax.servlet.http.HttpServletRequest;

public interface JWTAuthenticationService {

    String login(UserCredentials userCredentials) throws AuthenticationException;

    void logout(HttpServletRequest httpServletRequest) throws AuthenticationException, DataNotFoundException;
}
