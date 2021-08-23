package com.example.atm.service;

import com.example.atm.model.TransactionModel;
import com.example.atm.model.entity.TransactionEntity;

import java.util.List;

public interface TransactionService {

    TransactionEntity sendMoney(TransactionModel transactionModel);

    TransactionEntity uploadMoney(TransactionModel transactionModel);

    TransactionEntity borrowMoney(TransactionModel transactionModel);

    TransactionEntity returnMoney(TransactionModel transactionModel);

    TransactionEntity receiveMoney(TransactionModel transactionModel);

    List<TransactionEntity> getByUserId(Long userId);

    List<TransactionEntity> getBySenderCardCode(String senderCardCode);

    List<TransactionEntity> getByReceiverCardCode(String receiverCardCode);

    void deleteById(Long id);

    void save(TransactionEntity transactionEntity);
}
