package com.example.atm.config.security;

import com.example.atm.config.security.service.JWTTokenService;
import com.example.atm.repository.JWTTokenRepository;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.authentication.AuthenticationManagerBeanDefinitionParser;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JWTAuthFilter extends OncePerRequestFilter {

    @Autowired
    private JWTTokenService jwtTokenService;
    @Autowired
    private JWTTokenRepository jwtTokenRepository;


    @SneakyThrows
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (request.getRequestURI().endsWith("/login")) {
            filterChain.doFilter(request, response);
        }
        final String token = jwtTokenService.getTokenFromRequest(request);
        if (jwtTokenService.isValid(token)) {
            jwtTokenRepository.saveToken(token);
            UsernamePasswordAuthenticationToken t = new UsernamePasswordAuthenticationToken(jwtTokenService.getPrincipal(token), null);
            SecurityContextHolder
                    .getContext()
                    .setAuthentication(t);
        }
    }

}
