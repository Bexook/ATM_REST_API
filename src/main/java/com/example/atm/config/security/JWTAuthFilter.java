package com.example.atm.config.security;

import com.example.atm.config.security.service.JWTTokenService;
import com.example.atm.exceptions.ExpiredTokenException;
import io.jsonwebtoken.JwtException;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JWTAuthFilter extends OncePerRequestFilter {

    @Autowired
    private JWTTokenService jwtTokenService;


    @Override
    @SneakyThrows
    @ResponseStatus(HttpStatus.UNAUTHORIZED)  // 403
    @ExceptionHandler({ExpiredTokenException.class, JwtException.class})
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (request.getRequestURI().endsWith("/login") || request.getRequestURI().endsWith("/logout")) {
            filterChain.doFilter(request, response);
        } else {
            final String token = jwtTokenService.getTokenFromRequest(request);
            try {
                if (jwtTokenService.isValid(token)) {
                    UsernamePasswordAuthenticationToken t = new UsernamePasswordAuthenticationToken(jwtTokenService.getPrincipal(token), null);
                    SecurityContextHolder
                            .getContext()
                            .setAuthentication(t);
                    filterChain.doFilter(request, response);
                } else {
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
                }
            } catch (JwtException jwtException) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, jwtException.getMessage());
            }
        }

    }

}
