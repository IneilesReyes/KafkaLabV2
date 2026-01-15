package org.lab.transactionservice.controller;

import org.lab.transactionservice.client.TransactionEvent;
import org.lab.transactionservice.repository.TransactionRepository;
import org.lab.transactionservice.repository.entities.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    private KafkaTemplate<String, TransactionEvent> kafkaTemplate;
    private static final String TOPIC = "transaction-events";

    @PostMapping("/transaction-event")
    public Transaction createOrder(@RequestBody Transaction transaction) {
        Transaction registeredTransactions = transactionRepository.save(transaction);

        TransactionEvent event = new TransactionEvent(
                transaction.getTransactionId(),
                transaction.getAccountId(),
                transaction.getDebitAmount()
        );
        kafkaTemplate.send(TOPIC, String.valueOf(event.getAccountId()), event);
        return registeredTransactions;


    }

}
