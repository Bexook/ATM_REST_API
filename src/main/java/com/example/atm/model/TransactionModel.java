package com.example.atm.model;


import com.example.atm.model.enums.TransactionType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;


import javax.validation.constraints.Size;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionModel {

    @JsonProperty("sender")
    @Size(min = 5,max = 19)
    private String sender;
    @org.springframework.lang.NonNull
    @JsonProperty("receiver")
    @Size(min = 5,max = 19)
    private String receiver;
    @NonNull
    @JsonProperty("transactionType")
    private TransactionType transactionType;
    @JsonProperty("amount")
    private BigDecimal amount;

}
