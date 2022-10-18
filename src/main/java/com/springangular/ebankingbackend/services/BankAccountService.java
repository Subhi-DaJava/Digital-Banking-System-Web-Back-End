package com.springangular.ebankingbackend.services;

import com.springangular.ebankingbackend.dtos.CustomerDTO;
import com.springangular.ebankingbackend.entities.BankAccount;
import com.springangular.ebankingbackend.entities.CurrentAccount;
import com.springangular.ebankingbackend.entities.Customer;
import com.springangular.ebankingbackend.entities.SavingAccount;
import com.springangular.ebankingbackend.enums.TransactionType;
import com.springangular.ebankingbackend.exceptions.BalanceNotSufficientException;
import com.springangular.ebankingbackend.exceptions.BankAccountNotFoundException;
import com.springangular.ebankingbackend.exceptions.CustomerNotFoundException;

import java.util.List;

public interface BankAccountService {

  CustomerDTO saveCustomer(CustomerDTO customerDTO);

  CurrentAccount saveCurrentBankAccount(double initialBalance, double overDraft, Long customerId) throws CustomerNotFoundException;
  SavingAccount saveSavingBankAccount(double initialBalance, double interestRate, Long customerId) throws CustomerNotFoundException;

  List<CustomerDTO> listCustomer();

  BankAccount getBankAccount(String id) throws BankAccountNotFoundException;

  void debit(String accountId, double amount, String description, TransactionType transactionType) throws BankAccountNotFoundException, BalanceNotSufficientException;
  void credit(String accountId, double amount, String description, TransactionType transactionType) throws BankAccountNotFoundException;
  void transfer(String accountIdSource, String accountIdDestination, double amount, String description) throws BankAccountNotFoundException, BalanceNotSufficientException;

  List<BankAccount> listBankAccounts();

  CustomerDTO getCustomer(Long customerId) throws CustomerNotFoundException;

  CustomerDTO updateCustomer(CustomerDTO customerDTO);

  void deleteCustomer(Long customerId) throws CustomerNotFoundException;
}
