package com.springangular.ebankingbackend;

import com.springangular.ebankingbackend.dtos.BankAccountDTO;
import com.springangular.ebankingbackend.dtos.CurrentBankAccountDTO;
import com.springangular.ebankingbackend.dtos.CustomerDTO;
import com.springangular.ebankingbackend.dtos.SavingBankAccountDTO;
import com.springangular.ebankingbackend.entities.*;
import com.springangular.ebankingbackend.enums.AccountStatus;
import com.springangular.ebankingbackend.enums.OperationType;
import com.springangular.ebankingbackend.enums.TransactionType;
import com.springangular.ebankingbackend.exceptions.BalanceNotSufficientException;
import com.springangular.ebankingbackend.exceptions.BankAccountNotFoundException;
import com.springangular.ebankingbackend.exceptions.CustomerNotFoundException;
import com.springangular.ebankingbackend.repositories.AccountOperationRepository;
import com.springangular.ebankingbackend.repositories.BankAccountRepository;
import com.springangular.ebankingbackend.repositories.CustomerRepository;
import com.springangular.ebankingbackend.services.BankAccountService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

@SpringBootApplication
public class EbankingBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(EbankingBackendApplication.class, args);
	}

		// Sava the Customers and BankAccounts with DOTs, update the insert
	/*@Bean
	CommandLineRunner start (BankAccountService bankAccountService) {
		return args -> {
			Stream.of("Polat", "Ibrahim", "Gulqiz", "Aygul").forEach(name -> {
				CustomerDTO customerDTO = new CustomerDTO();
				customerDTO.setName(name);
				customerDTO.setEmail(name + "@gmail.com");
				bankAccountService.saveCustomer(customerDTO);
			});
			// 4 tours au total
			bankAccountService.listCustomer().forEach( customer -> {
				try {
					bankAccountService.saveCurrentBankAccount(Math.random() * 90000, 9000, customer.getId());
					bankAccountService.saveSavingBankAccount(Math.random() * 85000, 3.2, customer.getId());
				} catch (CustomerNotFoundException e) {
					e.printStackTrace();
				}
			});

			try {
				List<BankAccountDTO> bankAccountList = bankAccountService.getListBankAccounts();
				for (BankAccountDTO bankAccount : bankAccountList) {
					for (int i = 0; i < 10; i++) {
						String accountId;
						if(bankAccount instanceof SavingBankAccountDTO) {
							accountId = ((SavingBankAccountDTO) bankAccount).getId();
						} else {
							accountId = ((CurrentBankAccountDTO) bankAccount).getId();
						}
						bankAccountService.credit(
								accountId,
								10000 + Math.random() * 120000,
								"First Credit",
								Math.random() > 0.8 ? TransactionType.CARD : (Math.random() > 0.5 ? TransactionType.CASH : TransactionType.CHECK));

						bankAccountService.debit(
								accountId,
								1000 + Math.random() * 9000,
								"First Debit",
								Math.random() > 0.8 ? TransactionType.CARD : (Math.random() > 0.5 ? TransactionType.CASH : TransactionType.CHECK));

					}
				}
			} catch (BalanceNotSufficientException | BankAccountNotFoundException e) {
				e.printStackTrace();
			}
		};
	}*/

	// Sava the Customers with CustomersDOTs
	/*@Bean
	CommandLineRunner start (BankAccountService bankAccountService) {
		return args -> {
			Stream.of("PolatDTO", "IbrahimDTO", "GulqizDTO", "AygulDTO").forEach(name -> {
				CustomerDTO customerDTO = new CustomerDTO();
				customerDTO.setName(name);
				customerDTO.setEmail(name + "@gmail.com");
				bankAccountService.saveCustomer(customerDTO);
			});
			// 4 tours au total
			bankAccountService.listCustomer().forEach( customer -> {
				try {
					bankAccountService.saveCurrentBankAccount(Math.random() * 90000, 9000, customer.getId());
					bankAccountService.saveSavingBankAccount(Math.random() * 85000, 3.2, customer.getId());

					// 1er tour 10 accounts, 2ᵉ tour 12 accounts, 3ᵉ tour 14 accounts, 4ᵉ tour 16 accounts
					List<BankAccount> bankAccountList = bankAccountService.listBankAccounts();
					for (BankAccount bankAccount : bankAccountList) {
						for (int i = 0; i < 3; i++) {
							bankAccountService.credit(
									bankAccount.getId(),
									10000 + Math.random() * 120000,
									"First Credit",
									Math.random() > 0.8 ? TransactionType.CARD : (Math.random() > 0.5 ? TransactionType.CASH : TransactionType.CHECK));

							bankAccountService.debit(
									bankAccount.getId(),
									1000 + Math.random() * 9000,
									"First Debit",
									Math.random() > 0.8 ? TransactionType.CARD : (Math.random() > 0.5 ? TransactionType.CASH : TransactionType.CHECK));

						}
					}
				} catch (BalanceNotSufficientException | BankAccountNotFoundException | CustomerNotFoundException e) {
					e.printStackTrace();
				}
			});
		};

	}*/

	// Test the Service layer
	/*@Bean
	CommandLineRunner start (BankAccountService bankAccountService) {
		return args -> {
			Stream.of("Polat", "Ibrahim", "Gulqiz", "Aygul").forEach(name -> {
				Customer customer = new Customer();
				customer.setName(name);
				customer.setEmail(name + "@gmail.com");
				bankAccountService.saveCustomer(customer);
			});
			// 4 tours au total
			bankAccountService.listCustomer().forEach( customer -> {
				try {
					bankAccountService.saveCurrentBankAccount(Math.random() * 90000, 9000, customer.getId());
					bankAccountService.saveSavingBankAccount(Math.random() * 85000, 3.2, customer.getId());

					// 1er tour 2 accounts, 2ᵉ tour 4 accounts, 3ᵉ tour 6 accounts, 4ᵉ tour 8 accounts
					List<BankAccount> bankAccountList = bankAccountService.listBankAccounts();
					for (BankAccount bankAccount : bankAccountList) {
						for (int i = 0; i < 3; i++) {
							bankAccountService.credit(
									bankAccount.getId(),
									10000 + Math.random() * 120000,
									"First Credit",
									Math.random() > 0.8 ? TransactionType.CARD : (Math.random() > 0.5 ? TransactionType.CASH : TransactionType.CHECK));

							bankAccountService.debit(
									bankAccount.getId(),
									1000 + Math.random() * 9000,
									"First Debit",
									Math.random() > 0.8 ? TransactionType.CARD : (Math.random() > 0.5 ? TransactionType.CASH : TransactionType.CHECK));

						}
					}
				} catch (BalanceNotSufficientException | BankAccountNotFoundException | CustomerNotFoundException e) {
					e.printStackTrace();
				}
			});
		};

	}*/
// The test for a search of the bankAccount Current or Saving
/*	@Bean
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
		}*/
	// Insert the date base
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
