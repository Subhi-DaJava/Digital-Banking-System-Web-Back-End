package com.springangular.ebankingbackend.dtos;

import lombok.Data;
import javax.persistence.*;

@Data
public class CustomerDTO {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
}
