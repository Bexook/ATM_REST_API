package com.example.atm.config.security.service.impl;

import com.example.atm.config.security.service.JWTTokenService;
import com.example.atm.exceptions.DataNotFoundException;
import com.example.atm.exceptions.ExpiredTokenException;
import com.example.atm.repository.JWTTokenRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.logging.log4j.util.Strings;
import org.apache.tomcat.websocket.AuthenticationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Objects;
import java.util.function.Function;

@Service
public class JWTTokenServiceImpl implements JWTTokenService {

    private final String AUTHORIZATION = "Authorization";

    @Value("${jwt.secret}")
    private String secretKey;
    @Value("${jwt.expiration}")
    private int expirationPeriod;

    @Autowired
    private JWTTokenRepository jwtTokenRepository;


    @Override
    public String generateToken(String login) throws ExpiredTokenException {
        Date expiration = Date.from(Instant.from(LocalDate.now().plusDays(expirationPeriod).atStartOfDay(ZoneId.systemDefault())));
        String token = Jwts.builder()
                .setExpiration(expiration)
                .setSubject(login)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
        try {
            jwtTokenRepository.saveToken(token);
        } catch (RuntimeException e) {
            jwtTokenRepository.getToken(token);
        }
        return token;
    }

    @Override
    public void logoutToken(String token) throws DataNotFoundException {
        jwtTokenRepository.deleteToken(token);
        SecurityContextHolder.getContext().getAuthentication().setAuthenticated(false);
    }

    @Override
    public boolean isValid(String token) throws ExpiredTokenException {
        Claims claims = getClaims(token);
        String tokenFromDB = jwtTokenRepository.getToken(token);
        return new Date(System.currentTimeMillis()).before(claims.getExpiration()) &&
                Objects.nonNull(tokenFromDB);
    }

    @Override
    public String getTokenFromRequest(HttpServletRequest httpServletRequest) throws AuthenticationException {
        String token = httpServletRequest.getHeader(AUTHORIZATION);
        if (Objects.nonNull(token) && Strings.isNotBlank(token) && token.startsWith("Bearer ")) {
            return token.substring(7);
        }
        throw new AuthenticationException("Bearer token not found");
    }

    @Override
    public String getPrincipal(String token) {
        return claimsOperation(Claims::getSubject, getClaims(token));
    }


    private Claims getClaims(String token) {
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody();
    }


    private <T> T claimsOperation(Function<Claims, T> claimsFunction, Claims claims) {
        return claimsFunction.apply(claims);
    }

}
