package com.example.atm.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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

}
