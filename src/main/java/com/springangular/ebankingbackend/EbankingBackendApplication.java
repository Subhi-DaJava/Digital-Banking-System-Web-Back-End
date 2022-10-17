package com.springangular.ebankingbackend;

import com.springangular.ebankingbackend.entities.*;
import com.springangular.ebankingbackend.enums.AccountStatus;
import com.springangular.ebankingbackend.enums.OperationType;
import com.springangular.ebankingbackend.enums.TransactionType;
import com.springangular.ebankingbackend.repositories.AccountOperationRepository;
import com.springangular.ebankingbackend.repositories.BankAccountRepository;
import com.springangular.ebankingbackend.repositories.CustomerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Date;
import java.util.UUID;
import java.util.stream.Stream;

@SpringBootApplication
public class EbankingBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(EbankingBackendApplication.class, args);
	}

	@Bean
	CommandLineRunner start(BankAccountRepository bankAccountRepository) {
			return args -> {
				BankAccount bankAccount = bankAccountRepository.findById("0cd3f6b5-be58-481a-8852-07656fb0bc78").orElse(null);

				if (bankAccount instanceof CurrentAccount) {
					System.out.println("Over Draft => " + ((CurrentAccount) bankAccount).getOverDraft());
				} else if (bankAccount instanceof SavingAccount) {
					System.out.println("Rate Interest => " + ((SavingAccount) bankAccount).getInterestRate());
				} else {
					throw new IllegalArgumentException("There is no the BankAccount with this id");
				}

				System.out.println("This Account infos: ");
				System.out.println(bankAccount.getId());
				System.out.println(bankAccount.getCreatedDate());
				System.out.println(bankAccount.getBalance());
				System.out.println(bankAccount.getStatus());
				System.out.println(bankAccount.getCustomer().getName());
				System.out.println(bankAccount.getClass().getSimpleName());

				bankAccount.getAccountOperations().forEach(op -> {
					System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
					System.out.println(
							op.getId() + "\t" + op.getOperationDate() + "\t" + op.getAmount() + "\t" + op.getOperationType() + "\t" + op.getTransactionType());

				});
			};
		}
	/*@Bean
	CommandLineRunner start(CustomerRepository customerRepository,
							BankAccountRepository bankAccountRepository,
							AccountOperationRepository accountOperationRepository) {
		return args -> {
			Stream.of("Alim", "Memet", "Guzel").forEach(name -> {
				Customer customer = new Customer();
				customer.setName(name);
				customer.setEmail(name +"@gmail.com");
				customerRepository.save(customer);
			});
			customerRepository.findAll().forEach(customer -> {
				CurrentAccount currentAccount = new CurrentAccount();
				currentAccount.setId(UUID.randomUUID().toString());
				currentAccount.setBalance(Math.random() * 9000);
				currentAccount.setCreatedDate(new Date());
				currentAccount.setStatus(AccountStatus.CREATED);
				currentAccount.setCustomer(customer);
				currentAccount.setOverDraft(8000);
				bankAccountRepository.save(currentAccount);

				SavingAccount savingAccount = new SavingAccount();
				savingAccount.setId(UUID.randomUUID().toString());
				savingAccount.setBalance(Math.random() * 10000);
				savingAccount.setCreatedDate(new Date());
				savingAccount.setStatus(AccountStatus.CREATED);
				savingAccount.setCustomer(customer);
				savingAccount.setInterestRate(4.3);
				bankAccountRepository.save(savingAccount);

			});

			bankAccountRepository.findAll().forEach(account -> {
				for (int i = 0; i < 10; i++) {
					AccountOperation accountOperation = new AccountOperation();
					accountOperation.setOperationDate(new Date());
					accountOperation.setAmount(Math.random() * 12000);
					accountOperation.setOperationType(Math.random() > 0.5 ? OperationType.DEBIT : OperationType.CREDIT);
					accountOperation.setTransactionType(Math.random() > 0.8 ? TransactionType.CARD : (Math.random() > 0.5 ? TransactionType.CASH : TransactionType.TRANSFER));
					accountOperation.setBankAccount(account);
					accountOperationRepository.save(accountOperation);
				}
			});
		};
	}*/
}
