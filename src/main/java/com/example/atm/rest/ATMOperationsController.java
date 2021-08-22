//package com.example.atm.rest;
//
//import com.example.atm.model.UserCredentials;
//import com.example.atm.model.entity.TransactionEntity;
//import com.example.atm.repository.UserRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.web.bind.annotation.*;
//
//import java.math.BigDecimal;
//import java.util.List;
//
//@RestController
//@RequestMapping("/account/operation")
//public class ATMOperationsController {
//
//    @Autowired
//    private UserRepository userRepository;
//
//
//    @GetMapping("/get")
//    public BigDecimal getMoney() {
//        SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        return BigDecimal.ONE;
//    }
//
//    @GetMapping("/borrow")
//    public Object borrowMoney() {
//        return new Object();
//    }
//
//    @GetMapping("/balance")
//    public BigDecimal getCurrentAccountBalance() {
//        return BigDecimal.ZERO;
//    }
//
//    @GetMapping("/transactions/list")
//    public List<TransactionEntity> getTransactions(){
//
//    }
//
//
//    @PostMapping("/send")
//    public void sendMoney() {
//
//    }
//
//
//    @PostMapping("/upload")
//    public void uploadMoney() {
//
//    }
//
//
//
//
//}
