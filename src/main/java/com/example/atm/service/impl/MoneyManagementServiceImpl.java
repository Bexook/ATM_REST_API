package com.example.atm.service.impl;

import com.example.atm.exceptions.DataNotFoundException;
import com.example.atm.model.TransactionModel;
import com.example.atm.model.entity.ClientCardEntity;
import com.example.atm.model.entity.TransactionEntity;
import com.example.atm.model.enums.TransactionStatus;
import com.example.atm.service.ClientCardService;
import com.example.atm.service.MoneyManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;

@Service
public class MoneyManagementServiceImpl implements MoneyManagementService {

    @Autowired
    private ClientCardService clientCardService;

    @Value("${rules.fee}")
    private BigDecimal fee;

    @Override
    public BigDecimal calculateFee(BigDecimal amount) {
        return amount.multiply(fee);
    }

    @Override
    public void borrowMoney(final BigDecimal amount, ClientCardEntity clientCardEntity, TransactionEntity transactionEntity) throws DataNotFoundException {
        final BigDecimal generalBorrowAmount = clientCardEntity.getBorrowedMoney().add(amount);
        if (generalBorrowAmount.compareTo(clientCardEntity.getBorrowLimit()) < 1) {
            clientCardEntity.setBorrowedMoney(generalBorrowAmount);
            clientCardEntity.setCurrentBalance(clientCardEntity.getCurrentBalance().add(amount));
            clientCardService.update(clientCardEntity);
            transactionEntity.setTransactionStatus(TransactionStatus.SUCCESS);
        } else {
            transactionEntity.setTransactionStatus(TransactionStatus.EXCEEDED_BORROW_LIMIT);
        }
    }

    @Override
    public TransactionEntity sendMoney(TransactionModel transactionModel) throws DataNotFoundException {
        final BigDecimal fee = calculateFee(transactionModel.getAmount());
        final BigDecimal amountIncludingFee = transactionModel.getAmount().add(fee);
        ClientCardEntity sender = clientCardService.getByCardCode(transactionModel.getSender());
        ClientCardEntity receiver = clientCardService.getByCardCode(transactionModel.getReceiver());
        TransactionEntity transactionEntity = TransactionEntity.createTransaction(transactionModel, TransactionStatus.SUCCESS, fee, sender.getUserId());
        if (sender.getValidTo().compareTo(new Date(System.currentTimeMillis())) == 1) {

            if (amountIncludingFee.compareTo(sender.getCurrentBalance()) <= 0) {
                sender.setCurrentBalance(sender.getCurrentBalance().subtract(amountIncludingFee));
                clientCardService.update(sender);
                receiver.setCurrentBalance(receiver.getCurrentBalance().add(transactionModel.getAmount()));
                clientCardService.update(receiver);
                return transactionEntity;
            }
            transactionEntity.setTransactionStatus(TransactionStatus.DECLINED);
            return transactionEntity;
        }
        transactionEntity.setTransactionStatus(TransactionStatus.NOT_ENOUGH_MONEY);
        return transactionEntity;
    }


    @Override
    public void uploadMoney(BigDecimal amount, ClientCardEntity clientCardEntity) throws DataNotFoundException {
        if (amount.compareTo(BigDecimal.ZERO) != -1) {
            clientCardEntity.setCurrentBalance(clientCardEntity.getCurrentBalance().add(amount));
            clientCardService.update(clientCardEntity);
        }
    }

    @Override
    public void returnMoney(BigDecimal amount, ClientCardEntity clientCardEntity, TransactionEntity transactionEntity) throws DataNotFoundException {
        if (amount.compareTo(BigDecimal.ZERO) != -1) {
            if (amount.compareTo(clientCardEntity.getBorrowedMoney()) == 1) {
                final BigDecimal oddMoney = clientCardEntity.getBorrowedMoney().subtract(amount);
                clientCardEntity.setBorrowedMoney(BigDecimal.ZERO);
                clientCardEntity.setCurrentBalance(clientCardEntity.getCurrentBalance().add(oddMoney.abs()));
            } else {
                clientCardEntity.setBorrowedMoney(clientCardEntity.getBorrowedMoney().subtract(amount));
                clientCardEntity.setBorrowLimit(clientCardEntity.getBorrowLimit().add(amount));
            }
            clientCardService.update(clientCardEntity);
            transactionEntity.setTransactionStatus(TransactionStatus.SUCCESS);
        }
    }

    @Override
    public void changeBorrowLimit(BigDecimal amount, ClientCardEntity clientCardEntity) throws DataNotFoundException {
        if (amount.compareTo(BigDecimal.ZERO) != -1) {
            clientCardEntity.setBorrowLimit(amount);
            clientCardService.update(clientCardEntity);
        }
    }
}
