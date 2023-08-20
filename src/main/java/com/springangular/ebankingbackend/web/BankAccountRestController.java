package com.springangular.ebankingbackend.web;

import com.springangular.ebankingbackend.dtos.*;
import com.springangular.ebankingbackend.enums.AccountStatus;
import com.springangular.ebankingbackend.exceptions.BalanceNotSufficientException;
import com.springangular.ebankingbackend.exceptions.BankAccountNotFoundException;
import com.springangular.ebankingbackend.exceptions.CustomerNotFoundException;
import com.springangular.ebankingbackend.services.BankAccountService;
import lombok.extern.slf4j.Slf4j;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
//@CrossOrigin("*")
//@CrossOrigin("http://localhost:4200")
public class BankAccountRestController {

    private final BankAccountService bankAccountService;
    public BankAccountRestController(BankAccountService bankAccountService) {
        this.bankAccountService = bankAccountService;
    }
    @PreAuthorize("hasAnyAuthority('SCOPE_USER')")
    @GetMapping("/accounts/{id}")
    public BankAccountDTO getBankAccount(@PathVariable String id) throws BankAccountNotFoundException {
        log.info("BankAccountDTO is returned");
        return bankAccountService.getBankAccount(id);
    }
    @PreAuthorize("hasAnyAuthority('SCOPE_USER')")
    @GetMapping("/accounts")
    public List<BankAccountDTO> getListBankAccounts() {
        return bankAccountService.getListBankAccounts();
    }
    @PreAuthorize("hasAnyAuthority('SCOPE_USER')")
    @GetMapping("/accounts/{accountId}/operations")
    public List<AccountOperationDTO> getHistoryByList(@PathVariable String accountId) {
        return bankAccountService.getAccountHistoryByList(accountId);
    }
    @PreAuthorize("hasAnyAuthority('SCOPE_USER')")
    @GetMapping("/accounts/{accountId}/pageOperations")
    public AccountHistoryDTO getAccountHistoryByPage(
            @PathVariable String accountId,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "5") int size) throws BankAccountNotFoundException {


       return bankAccountService.getAccountHistoryByPage(accountId, page, size);
    }
    @PreAuthorize("hasAnyAuthority('SCOPE_ADMIN')")
    @PostMapping("/customers/{customerId}/current-accounts")
    public CurrentBankAccountDTO saveCurrentBankAccount(
            @RequestParam double initialBalance,
            @RequestParam double overDraft,
            @PathVariable Long customerId) throws CustomerNotFoundException {
        log.info("A Current Account has been successfully");
        return bankAccountService.saveCurrentBankAccount(initialBalance, overDraft, customerId);
    }
    @PreAuthorize("hasAnyAuthority('SCOPE_ADMIN')")
    @PostMapping("/customers/{customerId}/saving-accounts")
    public SavingBankAccountDTO saveSavingBankAccount(
            @RequestParam double initialBalance,
            @RequestParam double interestRate,
            @PathVariable Long customerId) throws CustomerNotFoundException {
        log.info("A Saving Account has been successfully");

        return bankAccountService.saveSavingBankAccount(initialBalance, interestRate, customerId);
    }
@PreAuthorize("hasAnyAuthority('SCOPE_ADMIN')")
    @PutMapping("/accounts/{accountId}")
    public BankAccountDTO updateBankAccount(@PathVariable String accountId, @RequestParam AccountStatus accountStatus) throws BankAccountNotFoundException {
        log.info("Update a bank account return with the account type");
        return bankAccountService.updateBankAccount(accountId, accountStatus);
    }

    @PreAuthorize("hasAnyAuthority('SCOPE_ADMIN')")
    @PostMapping("/accounts/debit")
    public DebitDTO debit(@RequestBody DebitDTO debitDTO) throws BankAccountNotFoundException, BalanceNotSufficientException {
        bankAccountService.debit(
                debitDTO.getAccountId(),
                debitDTO.getAmount(),
                debitDTO.getDescription(),
                debitDTO.getTransactionType());
        log.info("Debit is successful");
        return debitDTO;
    }
    @PreAuthorize("hasAnyAuthority('SCOPE_ADMIN')")
    @PostMapping("/accounts/credit")
    public CreditDTO credit(@RequestBody CreditDTO creditDTO) throws BankAccountNotFoundException, BalanceNotSufficientException {
        bankAccountService.credit(
                creditDTO.getAccountId(),
                creditDTO.getAmount(),
                creditDTO.getDescription(),
                creditDTO.getTransactionType());
        log.info("Credit is successful");
        return creditDTO;
    }

    @PreAuthorize("hasAnyAuthority('SCOPE_ADMIN')")
    @PostMapping("/accounts/transfer")
    public void transfer(@RequestBody TransferRequestDTO transferRequestDTO) throws BankAccountNotFoundException, BalanceNotSufficientException {
        log.info("Transfer is successful between theses bankAccounts sourceId: "
                + transferRequestDTO.getAccountSource() + " and targetId: " + transferRequestDTO.getAccountTarget());
        bankAccountService.transfer(
                transferRequestDTO.getAccountSource(),
                transferRequestDTO.getAccountTarget(),
                transferRequestDTO.getAmount(),
                transferRequestDTO.getDescription(),
                transferRequestDTO.getTransactionType());
    }
    @PreAuthorize("hasAnyAuthority('SCOPE_USER')")
    @GetMapping("/customers/{customerId}/accounts")
    public List<BankAccountDTO> getBankAccountsByCustomerId(@PathVariable Long customerId) {
        List<BankAccountDTO> bankAccountDTOS = bankAccountService.getBankAccountsByCustomerId(customerId);
        log.info("All accounts of the customer with customerId:{} have been successfully retrieved", customerId);
        return bankAccountDTOS;
    }

}
