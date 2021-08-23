package com.example.atm.model.entity;

import com.example.atm.model.TransactionModel;
import com.example.atm.model.enums.TransactionStatus;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import javax.management.ConstructorParameters;
import java.math.BigDecimal;
import java.sql.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionEntity {

    private Long id;
    private String senderCardCode;
    private String receiverCardCode;
    private BigDecimal amount;
    private BigDecimal fee;
    private Date date;
    private Long userId;
    private TransactionStatus transactionStatus;

    public static TransactionEntity createTransaction(
            TransactionModel transactionModel,
            TransactionStatus transactionStatus,
            BigDecimal fee,
            Long userId) {
        return new TransactionEntity(null,
                transactionModel.getSender(),
                transactionModel.getReceiver(),
                transactionModel.getAmount(),
                fee,
                new Date(System.currentTimeMillis()),
                userId,
                transactionStatus
        );
    }

}
