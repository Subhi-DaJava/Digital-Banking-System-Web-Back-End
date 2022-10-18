package com.springangular.ebankingbackend.services;

import com.springangular.ebankingbackend.dtos.CustomerDTO;
import com.springangular.ebankingbackend.entities.*;
import com.springangular.ebankingbackend.enums.OperationType;
import com.springangular.ebankingbackend.enums.TransactionType;
import com.springangular.ebankingbackend.exceptions.BalanceNotSufficientException;
import com.springangular.ebankingbackend.exceptions.BankAccountNotFoundException;
import com.springangular.ebankingbackend.exceptions.CustomerNotFoundException;
import com.springangular.ebankingbackend.mappers.BankAccountMapperImpl;
import com.springangular.ebankingbackend.repositories.AccountOperationRepository;
import com.springangular.ebankingbackend.repositories.BankAccountRepository;
import com.springangular.ebankingbackend.repositories.CustomerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
public class BankAccountServiceImpl implements BankAccountService{
    private BankAccountRepository bankAccountRepository;
    private CustomerRepository customerRepository;
    private AccountOperationRepository accountOperationRepository;
    private BankAccountMapperImpl dtoMapper;

    // same as @AllArgsConstructor of Lombok
    public BankAccountServiceImpl(BankAccountRepository bankAccountRepository,
                                  CustomerRepository customerRepository,
                                  AccountOperationRepository accountOperationRepository, BankAccountMapperImpl dtoMapper) {
        this.bankAccountRepository = bankAccountRepository;
        this.customerRepository = customerRepository;
        this.accountOperationRepository = accountOperationRepository;
        this.dtoMapper = dtoMapper;
    }
    // same as @slf4j api write directly log.info("");
    private static final Logger LOGGER = LoggerFactory.getLogger(BankAccountServiceImpl.class); // (this.getClass.getName())

    @Override
    public CustomerDTO saveCustomer(CustomerDTO customerDTO) {
        LOGGER.info("Saving new Customer");
        Customer customer = dtoMapper.fromCustomerDTO(customerDTO);
        Customer savedCustomer = customerRepository.save(customer);
        return dtoMapper.fromCustomer(savedCustomer);
    }

    @Override
    public CurrentAccount saveCurrentBankAccount(double initialBalance, double overDraft, Long customerId) throws CustomerNotFoundException {
        Customer customer = customerRepository.findById(customerId).orElse(null);
        if(customer == null) {
            throw new CustomerNotFoundException("Customer not found");
        }
        CurrentAccount currentAccount = new CurrentAccount();

        currentAccount.setId(UUID.randomUUID().toString());
        currentAccount.setCreatedDate(new Date());
        currentAccount.setBalance(initialBalance);
        currentAccount.setCustomer(customer);
        currentAccount.setOverDraft(overDraft);
        //currentAccount.setStatus(AccountStatus.CREATED);

        return bankAccountRepository.save(currentAccount);
    }

    @Override
    public SavingAccount saveSavingBankAccount(double initialBalance, double interestRate, Long customerId) throws CustomerNotFoundException {

        Customer customer = customerRepository.findById(customerId).orElse(null);
        if(customer == null) {
            throw new CustomerNotFoundException("Customer not found");
        }
        SavingAccount savingAccount = new SavingAccount();

        savingAccount.setId(UUID.randomUUID().toString());
        savingAccount.setCreatedDate(new Date());
        savingAccount.setBalance(initialBalance);
        savingAccount.setCustomer(customer);
        savingAccount.setInterestRate(interestRate);
        //savingAccount.setStatus(AccountStatus.CREATED);

        return bankAccountRepository.save(savingAccount);
    }


    @Override
    public List<CustomerDTO> listCustomer() {
        List<Customer> customers = customerRepository.findAll();
        List<CustomerDTO> customerDTOS = customers.stream()
                .map(customer -> dtoMapper.fromCustomer(customer)).collect(Collectors.toList());
        return customerDTOS;
    }

    @Override
    public BankAccount getBankAccount(String id) throws BankAccountNotFoundException {
        BankAccount bankAccount = bankAccountRepository.findById(id)
                .orElseThrow(() -> new BankAccountNotFoundException("No Bank Account with this id: " + id ));
        return bankAccount;
    }

    @Override
    public void debit(String accountId, double amount, String description, TransactionType transactionType) throws BankAccountNotFoundException, BalanceNotSufficientException {
        BankAccount bankAccount = getBankAccount(accountId);
        if(bankAccount.getBalance() < amount) {
            throw new BalanceNotSufficientException("Balance not sufficient");
        }
        AccountOperation debitAccountOperation = new AccountOperation();
        debitAccountOperation.setOperationType(OperationType.DEBIT);
        debitAccountOperation.setTransactionType(transactionType);
        debitAccountOperation.setAmount(amount);
        debitAccountOperation.setOperationDate(new Date());
        debitAccountOperation.setBankAccount(bankAccount);
        debitAccountOperation.setDescription(description);
        accountOperationRepository.save(debitAccountOperation);

        bankAccount.setBalance(bankAccount.getBalance() - amount);
        bankAccountRepository.save(bankAccount);
    }

    @Override
    public void credit(String accountId, double amount, String description, TransactionType transactionType) throws BankAccountNotFoundException {
        BankAccount bankAccount = getBankAccount(accountId);

        AccountOperation creditAccountOperation = new AccountOperation();
        creditAccountOperation.setOperationType(OperationType.CREDIT);
        creditAccountOperation.setTransactionType(transactionType);
        creditAccountOperation.setAmount(amount);
        creditAccountOperation.setOperationDate(new Date());
        creditAccountOperation.setBankAccount(bankAccount);
        creditAccountOperation.setDescription(description);
        accountOperationRepository.save(creditAccountOperation);

        bankAccount.setBalance(bankAccount.getBalance() + amount);
        bankAccountRepository.save(bankAccount);
    }

    @Override
    public void transfer(String accountIdSource, String accountIdDestination, double amount, String description) throws BankAccountNotFoundException, BalanceNotSufficientException {
        debit(accountIdDestination, amount, description, TransactionType.valueOf((TransactionType.TRANSFER + " to " + accountIdDestination)));
        credit(accountIdDestination, amount, description, TransactionType.valueOf(TransactionType.TRANSFER + " from " + accountIdSource));
    }

    @Override
    public List<BankAccount> listBankAccounts() {
        return bankAccountRepository.findAll();
    }
    @Override
    public CustomerDTO getCustomer(Long customerId) throws CustomerNotFoundException {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found with this id : " + customerId));
        return dtoMapper.fromCustomer(customer);
    }
    @Override
    public CustomerDTO updateCustomer(CustomerDTO customerDTO) {
        LOGGER.info("Updating a Customer");
        Customer customer = dtoMapper.fromCustomerDTO(customerDTO);
        Customer savedCustomer = customerRepository.save(customer);
        return dtoMapper.fromCustomer(savedCustomer);
    }
    @Override
    public void deleteCustomer(Long customerId) throws CustomerNotFoundException {
        customerRepository.findById(customerId).orElseThrow(
                () -> new CustomerNotFoundException("Customer not found with this id: " + customerId));
        customerRepository.deleteById(customerId);
    }

}
