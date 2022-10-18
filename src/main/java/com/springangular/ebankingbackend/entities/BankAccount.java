package com.springangular.ebankingbackend.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.springangular.ebankingbackend.enums.AccountStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "TYPE", length = 30, discriminatorType = DiscriminatorType.STRING) // length 255 par défaut et String
@Data @NoArgsConstructor @AllArgsConstructor
public abstract class BankAccount {
    @Id
    private String id; // RIB
    private double balance;
    private Date createdDate;
    @Enumerated(EnumType.STRING)
    private AccountStatus status;
    @ManyToOne
    private Customer customer;
    @OneToMany (mappedBy = "bankAccount") // fetch = FetchType.LAZY par défaut, fetch = FetchType.EAGER, RÔLE_ par exemple
    // @JsonProperty(access = JsonProperty.Access.WRITE_ONLY), crée DTO
    private List<AccountOperation> accountOperations;

}
