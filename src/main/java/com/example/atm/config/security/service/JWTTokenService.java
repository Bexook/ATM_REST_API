package com.example.atm.config.security.service;

import com.example.atm.model.UserCredentials;
import org.apache.tomcat.websocket.AuthenticationException;

import javax.servlet.http.HttpServletRequest;

public interface JWTTokenService {

    String generateToken(String login);

    void logoutToken(String token);

    boolean isValid(String token);

    String getTokenFromRequest(HttpServletRequest httpServletRequest) throws AuthenticationException;

    String getPrincipal(String token);

}
