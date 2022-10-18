package com.springangular.ebankingbackend.web;

import com.springangular.ebankingbackend.entities.Customer;
import com.springangular.ebankingbackend.services.BankAccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
public class CustomerRestController {
    private BankAccountService bankAccountService;

    public CustomerRestController(BankAccountService bankAccountService) {
        this.bankAccountService = bankAccountService;
    }
    @GetMapping("/customers")
    public List<Customer> customers() {
        log.info("Customers' list have been returned");
        return bankAccountService.listCustomer();
    }
}
