package com.springangular.ebankingbackend.web;

import com.springangular.ebankingbackend.dtos.AccountHistoryDTO;
import com.springangular.ebankingbackend.dtos.AccountOperationDTO;
import com.springangular.ebankingbackend.dtos.BankAccountDTO;
import com.springangular.ebankingbackend.exceptions.BankAccountNotFoundException;
import com.springangular.ebankingbackend.services.BankAccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
public class BankAccountRestController {
    private BankAccountService bankAccountService;

    public BankAccountRestController(BankAccountService bankAccountService) {
        this.bankAccountService = bankAccountService;
    }

    @GetMapping("/accounts/{id}")
    public BankAccountDTO getBankAccount(@PathVariable String id) throws BankAccountNotFoundException {
        log.info("BankAccountDTO is returned");
        return bankAccountService.getBankAccount(id);
    }

    @GetMapping("/accounts")
    public List<BankAccountDTO> getListBankAccounts() {
        return bankAccountService.getListBankAccounts();
    }

    @GetMapping("/accounts/{accountId}/operations")
    public List<AccountOperationDTO> getHistoryByList(@PathVariable String accountId) {
        return bankAccountService.getAccountHistoryByList(accountId);
    }

    @GetMapping("/accounts/{accountId}/pageOperations")
    public AccountHistoryDTO getAccountHistoryByPage(
            @PathVariable String accountId,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "5") int size) throws BankAccountNotFoundException {


       return bankAccountService.getAccountHistoryByPage(accountId, page, size);
    }

    @PostMapping("/accounts")
    public BankAccountDTO saveBankAccount(@RequestBody BankAccountDTO bankAccountDTO) {
        return null;
    }

    @PutMapping("/accounts/{accountId}")
    public BankAccountDTO updateBankAccount(@PathVariable String accountId, @RequestBody BankAccountDTO bankAccountDTO) {
        return null;
    }
}
