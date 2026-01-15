package org.lab.transactionservice.client;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TransactionEvent implements Serializable {
    private Long transactionId;
    private Long accountId;
    private Long amountDebit;

}
