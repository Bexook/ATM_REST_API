package com.example.atm.config.security.service;

import com.example.atm.exceptions.DataNotFoundException;
import com.example.atm.exceptions.ExpiredTokenException;
import com.example.atm.model.UserCredentials;
import org.apache.tomcat.websocket.AuthenticationException;

import javax.servlet.http.HttpServletRequest;

public interface JWTTokenService {

    String generateToken(String login) throws ExpiredTokenException;

    void logoutToken(String token) throws DataNotFoundException;

    boolean isValid(String token) throws DataNotFoundException, ExpiredTokenException;

    String getTokenFromRequest(HttpServletRequest httpServletRequest) throws AuthenticationException;

    String getPrincipal(String token);

}
