//package com.example.atm.exceptions.handler;
//
//import com.example.atm.exceptions.DataNotFoundException;
//import com.example.atm.exceptions.ExpiredTokenException;
//import io.jsonwebtoken.JwtException;
//import org.springframework.dao.DataIntegrityViolationException;
//import org.springframework.http.HttpStatus;
//import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.ResponseStatus;
//
//import java.sql.SQLException;
//
//@ControllerAdvice
//public class ATMExceptionServerHandling {
//
//    @ResponseStatus(HttpStatus.BAD_REQUEST)  // 400
//    @ExceptionHandler({DataNotFoundException.class, SQLException.class})
//    public void dataNotFound() {
//    }
//
//    @ResponseStatus(HttpStatus.UNAUTHORIZED)  // 401
//    @ExceptionHandler({ExpiredTokenException.class, JwtException.class})
//    public void tokenExpired() {
//    }
//
//}
