package com.example.atm.service;

import com.example.atm.exceptions.DataNotFoundException;
import com.example.atm.model.TransactionModel;
import com.example.atm.model.entity.TransactionEntity;

import java.util.List;

public interface TransactionService {

    TransactionEntity sendMoney(TransactionModel transactionModel) throws DataNotFoundException;

    TransactionEntity uploadMoney(TransactionModel transactionModel) throws DataNotFoundException;

    TransactionEntity borrowMoney(TransactionModel transactionModel) throws DataNotFoundException;

    TransactionEntity returnMoney(TransactionModel transactionModel) throws DataNotFoundException;

    TransactionEntity receiveMoney(TransactionModel transactionModel) throws DataNotFoundException;

    List<TransactionEntity> getByUserId(Long userId) throws DataNotFoundException;

    List<TransactionEntity> getBySenderCardCode(String senderCardCode) throws DataNotFoundException;

    List<TransactionEntity> getMergedTransactionList(String cardCode) throws DataNotFoundException;

    List<TransactionEntity> getByReceiverCardCode(String receiverCardCode) throws DataNotFoundException;

    void deleteById(Long id) throws DataNotFoundException;

    void save(TransactionEntity transactionEntity);
}
