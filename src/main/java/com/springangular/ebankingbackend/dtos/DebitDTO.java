package com.springangular.ebankingbackend.dtos;

import com.springangular.ebankingbackend.enums.TransactionType;
import lombok.Data;

@Data
public class DebitDTO {
    private String accountId;
    private double amount;
    private String description;
    private TransactionType transactionType;

}
