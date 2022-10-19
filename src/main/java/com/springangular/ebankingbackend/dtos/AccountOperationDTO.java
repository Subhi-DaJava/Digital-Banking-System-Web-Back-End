package com.springangular.ebankingbackend.dtos;

import com.springangular.ebankingbackend.enums.OperationType;
import com.springangular.ebankingbackend.enums.TransactionType;
import lombok.Data;

import java.util.Date;

@Data
public class AccountOperationDTO {

    private Long id;
    private Date operationDate;
    private double amount;
    private OperationType operationType;
    private TransactionType transactionType;
    private String description;
}
