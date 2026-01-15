package org.lab.accountservice.client;

import jakarta.transaction.Transactional;
import org.lab.accountservice.entities.Account;
import org.lab.accountservice.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class TransactionConsumer {

    @Autowired
    private AccountRepository accountRepository;

    @Transactional
    @KafkaListener(topics = "transaction-events", groupId = "transaction-group")
    public void consume(@Payload TransactionEvent transaction) {

        Account account = accountRepository.findById(transaction.getAccountId()).orElse(null);
        if (account == null) {
            System.out.println("transaction with id: " + transaction.getTransactionId() + " not found");
            return;
        }

        Long accountBalance = account.getBalance();
        if (!isValidTransaction(transaction.getAmountDebit(), accountBalance)) {
            System.out.println("transaction with id: " + transaction.getTransactionId() +
                    "could not be completed due to lack of funds");
            return;
        }
        account.setBalance(accountBalance - transaction.getAmountDebit());
        Account newAccount = accountRepository.save(account);

        System.out.println("Actual balance: " + newAccount.getBalance());

    }

    private boolean isValidTransaction(Long debitAmount, Long accountBalance) {
        return debitAmount >= accountBalance;
    }
}
