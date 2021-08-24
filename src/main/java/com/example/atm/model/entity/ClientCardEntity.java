package com.example.atm.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;
import java.sql.Date;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ClientCardEntity {

    private Long id;
    private String cardCode;
    private String password;
    private Integer CVVCode;
    private Date validTo;
    private BigDecimal currentBalance;
    private BigDecimal borrowLimit;
    private BigDecimal borrowedMoney;
    private Long userId;
    private boolean disabled;

}
