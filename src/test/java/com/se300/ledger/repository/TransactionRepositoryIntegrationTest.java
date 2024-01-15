package com.se300.ledger.repository;

import com.se300.ledger.SmartStoreApplication;
import com.se300.ledger.TestSmartStoreApplication;
import com.se300.ledger.model.Account;
import com.se300.ledger.model.Transaction;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = {TestSmartStoreApplication.class})
public class TransactionRepositoryIntegrationTest {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Test
    public void testTransactionRepository(){

        Account payer = new Account("payer", 60);
        Account payee = new Account("payee", 0);

        accountRepository.save(payer);
        accountRepository.save(payee);

        Transaction sergey = transactionRepository.save
                (new Transaction("1", 60, 10, "this is just a test", payer, payee ));

        assertEquals (sergey, transactionRepository.findById("1").get());
    }
}
