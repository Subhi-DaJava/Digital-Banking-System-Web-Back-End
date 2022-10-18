package com.springangular.ebankingbackend.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor
public class Customer {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    @OneToMany(mappedBy = "customer")
    //@JsonProperty(access = JsonProperty.Access.WRITE_ONLY) // c'est la raison pour laquelle on crée des DTOs, bonne pratique
    private List<BankAccount> bankAccounts;
}
