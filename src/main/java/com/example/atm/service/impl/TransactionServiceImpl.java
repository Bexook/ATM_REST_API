package com.example.atm.service.impl;

import com.example.atm.exceptions.DataNotFoundException;
import com.example.atm.model.TransactionModel;
import com.example.atm.model.entity.ClientCardEntity;
import com.example.atm.model.entity.TransactionEntity;
import com.example.atm.model.enums.TransactionStatus;
import com.example.atm.model.enums.TransactionType;
import com.example.atm.repository.TransactionRepository;
import com.example.atm.service.ClientCardService;
import com.example.atm.service.MoneyManagementService;
import com.example.atm.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private MoneyManagementService moneyManagementService;
    @Autowired
    private ClientCardService clientCardService;
    @Autowired
    private TransactionRepository transactionRepository;

    @Override
    public TransactionEntity sendMoney(TransactionModel transactionModel) throws DataNotFoundException {
        if (transactionModel.getTransactionType() == TransactionType.BETWEEN_CLIENTS) {
            if (((String) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).equals(transactionModel.getSender())) {
                TransactionEntity transactionEntity = moneyManagementService.sendMoney(transactionModel);
                save(transactionEntity);
                return transactionEntity;
            }
        }
        throw new IllegalArgumentException("Wrong transactionType");
    }

    @Override
    public TransactionEntity uploadMoney(TransactionModel transactionModel) throws DataNotFoundException {
        final String currentUser = SecurityContextHolder.getContext().getAuthentication().getName();
        if (TransactionType.UPLOAD_MONEY == transactionModel.getTransactionType() &&
                1 == transactionModel.getAmount().compareTo(BigDecimal.ZERO) &&
                transactionModel.getReceiver().equals(currentUser)
        ) {
            ClientCardEntity receiver = clientCardService.getByCardCode(transactionModel.getReceiver());
            TransactionEntity transactionEntity = TransactionEntity.createTransaction(transactionModel, TransactionStatus.SUCCESS, BigDecimal.ZERO, receiver.getUserId());
            if (receiver.getValidTo().compareTo(new Date(System.currentTimeMillis())) == 1) {
                moneyManagementService.uploadMoney(transactionModel.getAmount(), receiver);
                save(transactionEntity);
                return transactionEntity;
            }
            transactionEntity.setTransactionStatus(TransactionStatus.DECLINED);
            save(transactionEntity);
            return transactionEntity;
        }
        throw new IllegalArgumentException("Wrong transactionType");
    }

    @Override
    public TransactionEntity borrowMoney(TransactionModel transactionModel) throws DataNotFoundException {
        if (TransactionType.BORROW_MONEY == transactionModel.getTransactionType()) {
            ClientCardEntity clientCardEntity = clientCardService.getByCardCode(transactionModel.getReceiver());
            TransactionEntity transactionEntity = TransactionEntity.createTransaction(transactionModel, TransactionStatus.SUCCESS, BigDecimal.ZERO, clientCardEntity.getUserId());
            if (clientCardEntity.getValidTo().compareTo(new Date(System.currentTimeMillis())) == 1) {
                moneyManagementService.borrowMoney(transactionModel.getAmount(), clientCardEntity, transactionEntity);
                save(transactionEntity);
                return transactionEntity;
            }
            transactionEntity.setTransactionStatus(TransactionStatus.DECLINED);
            return transactionEntity;
        }
        throw new IllegalArgumentException("Wrong transactionType");
    }

    @Override
    public TransactionEntity returnMoney(TransactionModel transactionModel) throws DataNotFoundException {
        if (TransactionType.RETURN_MONEY == transactionModel.getTransactionType()) {
            ClientCardEntity clientCardEntity = clientCardService.getByCardCode(transactionModel.getReceiver());
            TransactionEntity transactionEntity = TransactionEntity.createTransaction(transactionModel, TransactionStatus.SUCCESS, BigDecimal.ZERO, clientCardEntity.getUserId());
            if (clientCardEntity.getValidTo().compareTo(new Date(System.currentTimeMillis())) == 1) {
                moneyManagementService.returnMoney(transactionModel.getAmount(), clientCardEntity, transactionEntity);
                save(transactionEntity);
                return transactionEntity;
            }
            transactionEntity.setTransactionStatus(TransactionStatus.DECLINED);
            save(transactionEntity);
            return transactionEntity;
        }

        throw new IllegalArgumentException("Wrong transactionType");
    }

    @Override
    public List<TransactionEntity> getByUserId(Long userId) throws DataNotFoundException {
        return transactionRepository.getByUserId(userId);
    }

    @Override
    public List<TransactionEntity> getBySenderCardCode(String senderCardCode) throws DataNotFoundException {
        return transactionRepository.getBySenderCardCode(senderCardCode);
    }

    @Override
    public List<TransactionEntity> getByReceiverCardCode(String receiverCardCode) throws DataNotFoundException {
        return transactionRepository.getByReceiverCardCode(receiverCardCode);
    }

    @Override
    public List<TransactionEntity> getMergedTransactionList(String cardCode) throws DataNotFoundException {
        List<TransactionEntity> transactions = getByReceiverCardCode(cardCode);
        transactions.addAll(getBySenderCardCode(cardCode));
        return transactions;
    }

    @Override
    public void deleteById(Long id) throws DataNotFoundException {
        transactionRepository.deleteById(id);
    }

    @Override
    public void save(TransactionEntity transactionEntity) {
        transactionRepository.save(Objects.requireNonNull(transactionEntity));
    }

    @Override
    public TransactionEntity receiveMoney(TransactionModel transactionModel) throws DataNotFoundException {
        if (TransactionType.RECEIVE_MONEY == transactionModel.getTransactionType()) {
            ClientCardEntity userCard = clientCardService.getByCardCode(transactionModel.getReceiver());
            TransactionEntity transactionEntity = TransactionEntity.createTransaction(transactionModel, TransactionStatus.SUCCESS, BigDecimal.ZERO, userCard.getUserId());
            if (userCard.getValidTo().compareTo(new Date(System.currentTimeMillis())) == 1) {
                transactionEntity.setUserId(userCard.getUserId());
                if (userCard.getCurrentBalance().compareTo(transactionModel.getAmount()) >= 0) {
                    userCard.setCurrentBalance(userCard.getCurrentBalance().subtract(transactionModel.getAmount()));
                    save(transactionEntity);
                    return transactionEntity;
                }
                transactionEntity.setTransactionStatus(TransactionStatus.NOT_ENOUGH_MONEY);
                transactionEntity.setUserId(userCard.getUserId());
                save(transactionEntity);
                return transactionEntity;
            }
            transactionEntity.setTransactionStatus(TransactionStatus.DECLINED);
            save(transactionEntity);
            return transactionEntity;
        }
        throw new IllegalArgumentException("Wrong transactionType");
    }


}
