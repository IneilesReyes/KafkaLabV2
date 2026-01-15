package org.lab.accountservice;

import org.lab.accountservice.entities.Account;
import org.lab.accountservice.repository.AccountRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class AccountServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(AccountServiceApplication.class, args);
    }

    @Bean
    CommandLineRunner initAccounts(AccountRepository accountRepository) {
        return args -> {
            accountRepository.save(new Account(1L, 1_000L));
            accountRepository.save(new Account(2L, 2_000L));
            accountRepository.save(new Account(3L, 3_000L));
            accountRepository.save(new Account(4L, 4_000L));
            accountRepository.save(new Account(5L, 5_000L));

            System.out.println("Accounts initialized in database!");
        };
    }

}
