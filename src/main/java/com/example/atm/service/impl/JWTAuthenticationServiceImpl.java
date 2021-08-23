package com.example.atm.service.impl;

import com.example.atm.config.security.service.JWTTokenService;
import com.example.atm.model.UserCredentials;
import com.example.atm.service.JWTAuthenticationService;
import lombok.SneakyThrows;
import org.apache.tomcat.websocket.AuthenticationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

@Service
public class JWTAuthenticationServiceImpl implements JWTAuthenticationService {

    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private JWTTokenService jwtTokenService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @SneakyThrows
    @Override
    public String login(UserCredentials creds) throws AuthenticationException {
        UserDetails userDetails = userDetailsService.loadUserByUsername(creds.getLogin());
        if (Objects.nonNull(userDetails) && passwordEncoder.matches(creds.getPassword(), userDetails.getPassword())) {
            return jwtTokenService.generateToken(creds.getLogin());
        }
        throw new AuthenticationException("Unknown user");
    }

    @Override
    public void logout(HttpServletRequest httpServletRequest) throws AuthenticationException {
        String token = jwtTokenService.getTokenFromRequest(httpServletRequest);
        jwtTokenService.logoutToken(token);
    }
}
