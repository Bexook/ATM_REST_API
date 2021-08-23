package com.example.atm.rest;

import com.example.atm.model.TransactionModel;
import com.example.atm.model.entity.ClientCardEntity;
import com.example.atm.model.entity.TransactionEntity;
import com.example.atm.service.ClientCardService;
import com.example.atm.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/account/operation")
public class ATMOperationsController {

    private final TransactionService transactionService;
    private final ClientCardService clientCardService;

    @GetMapping("/get")
    public ResponseEntity<TransactionEntity> getMoney(@RequestBody @Valid TransactionModel transactionModel) {
        return ResponseEntity.ok(transactionService.receiveMoney(transactionModel));
    }

    @GetMapping("/borrow")
    public ResponseEntity<TransactionEntity> borrowMoney(@RequestBody @Valid TransactionModel transactionModel) {
        return ResponseEntity.ok(transactionService.borrowMoney(transactionModel));
    }

    @GetMapping("/card")
    public ResponseEntity<ClientCardEntity> getCurrentAccountBalance(@RequestParam String cardCode) {
        return ResponseEntity.ok(clientCardService.getByCardCode(cardCode));
    }

    @GetMapping("/transactions/list")
    public ResponseEntity<List<TransactionEntity>> getTransactions(@RequestParam String senderCardCode) {
        return ResponseEntity.ok(transactionService.getBySenderCardCode(senderCardCode));
    }

    @PostMapping("/send")
    public ResponseEntity<TransactionEntity> sendMoney(@RequestBody @Valid TransactionModel transactionModel) {
        return ResponseEntity.ok(transactionService.sendMoney(transactionModel));
    }

    @PostMapping("/upload")
    public ResponseEntity<TransactionEntity> uploadMoney(@RequestBody @Valid TransactionModel transactionModel) {
        return ResponseEntity.ok(transactionService.uploadMoney(transactionModel));
    }

    @PostMapping("/return")
    public ResponseEntity<TransactionEntity> returnMoney(@RequestBody @Valid TransactionModel transactionModel) {
        return ResponseEntity.ok(transactionService.returnMoney(transactionModel));
    }

}
