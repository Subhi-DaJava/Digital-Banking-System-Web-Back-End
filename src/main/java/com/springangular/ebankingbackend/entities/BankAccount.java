package com.springangular.ebankingbackend.entities;

import com.springangular.ebankingbackend.enums.AccountStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "TYPE", length = 20, discriminatorType = DiscriminatorType.STRING) // length 255 par défaut et String
@Data @NoArgsConstructor @AllArgsConstructor
public class BankAccount {
    @Id
    private String id; // RIB
    private double balance;
    private Date createdDate;
    @Enumerated(EnumType.STRING)
    private AccountStatus status;
    @ManyToOne
    private Customer customer;
    @OneToMany (mappedBy = "bankAccount")
    private List<AccountOperation> accountOperations;

}
