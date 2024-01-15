package com.se300.ledger.repository;

import com.se300.ledger.model.Account;
import com.se300.ledger.model.Transaction;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = {FakeTransactionRepository.class})
public class TransactionRepositoryMockTest {

    @Autowired
    private TransactionRepository transactionRepository;

    @Test
    public void testMockedTransactionRepository() {

        Account dummyPayer = new Account("payer", 0);
        Account dummyPayee = new Account("payee", 0);

        Transaction transaction = new Transaction("1", 60, 10, "this is just a test",
                dummyPayer, dummyPayee);

        transactionRepository.save(transaction);
        assertEquals(transaction, transactionRepository.findById("1").get());

    }
}