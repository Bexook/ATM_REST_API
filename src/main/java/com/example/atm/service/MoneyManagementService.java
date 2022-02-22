package com.example.atm.service;

import com.example.atm.exceptions.DataNotFoundException;
import com.example.atm.model.TransactionModel;
import com.example.atm.model.entity.ClientCardEntity;
import com.example.atm.model.entity.TransactionEntity;

import java.math.BigDecimal;

public interface MoneyManagementService {

    BigDecimal calculateFee(final BigDecimal amount);

    void borrowMoney(final BigDecimal amount, ClientCardEntity clientCardEntity, TransactionEntity transactionEntity) throws DataNotFoundException;

    void uploadMoney(final BigDecimal amount, ClientCardEntity clientCardEntity) throws DataNotFoundException;

    void returnMoney(final BigDecimal amount, ClientCardEntity clientCardEntity,TransactionEntity transactionEntity) throws DataNotFoundException;

    TransactionEntity sendMoney(TransactionModel transactionModel) throws DataNotFoundException;

    void changeBorrowLimit(final BigDecimal amount, ClientCardEntity clientCardEntity) throws DataNotFoundException;


}
