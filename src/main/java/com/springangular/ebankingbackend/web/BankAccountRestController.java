package com.springangular.ebankingbackend.web;

import com.springangular.ebankingbackend.dtos.*;
import com.springangular.ebankingbackend.enums.AccountStatus;
import com.springangular.ebankingbackend.enums.TransactionType;
import com.springangular.ebankingbackend.exceptions.BalanceNotSufficientException;
import com.springangular.ebankingbackend.exceptions.BankAccountNotFoundException;
import com.springangular.ebankingbackend.exceptions.CustomerNotFoundException;
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

    @PostMapping("/customers/{customerId}/current-ccounts")
    public CurrentBankAccountDTO saveCurrentBankAccount(
            @RequestParam double initialBalance,
            @RequestParam double overDraft,
            @PathVariable Long customerId) throws CustomerNotFoundException {

        return bankAccountService.saveCurrentBankAccount(initialBalance, overDraft, customerId);
    }

    @PostMapping("/customers/{customerId}/saving-accounts")
    public SavingBankAccountDTO saveSavingBankAccount(
            @RequestParam double initialBalance,
            @RequestParam double interestRate,
            @PathVariable Long customerId) throws CustomerNotFoundException {

        return bankAccountService.saveSavingBankAccount(initialBalance, interestRate, customerId);
    }

    @PutMapping("/accounts/{accountId}")
    public BankAccountDTO updateBankAccount(@PathVariable String accountId, @RequestParam AccountStatus accountStatus) throws BankAccountNotFoundException {
        log.info("Update a bank account return with the account type");
        return bankAccountService.updateBankAccount(accountId, accountStatus);
    }
/*    @PostMapping("/accounts/{accountId}/debits")
    public void debit(
            @PathVariable String accountId,
            @RequestParam double amount,
            @RequestParam String description,
            @RequestParam TransactionType transactionType) throws BankAccountNotFoundException, BalanceNotSufficientException {
        log.info("Debit is successful with this accountId: " + accountId);
        bankAccountService.debit(accountId, amount, description, transactionType);
    }

    @PostMapping("/accounts/{accountId}/credits")
    public void credit(
            @PathVariable String accountId,
            @RequestParam double amount,
            @RequestParam String description,
            @RequestParam TransactionType transactionType) throws BankAccountNotFoundException, BalanceNotSufficientException {
        log.info("Credit is successful with this accountId: " + accountId);
        bankAccountService.debit(accountId, amount, description, transactionType);
    }*/

    @PostMapping("accounts/{sourceId}/transfers")
    public void transfer(
            @PathVariable String sourceId,
            @RequestParam String targetId,
            @RequestParam double amount,
            @RequestParam String description,
            @RequestParam TransactionType transactionType) throws BankAccountNotFoundException, BalanceNotSufficientException {
        log.info("Transfer is successful between theses bankAccounts sourceId: " + sourceId + " and targetId: " + targetId);
        bankAccountService.transfer(sourceId, targetId, amount, description, transactionType);
    }


}
