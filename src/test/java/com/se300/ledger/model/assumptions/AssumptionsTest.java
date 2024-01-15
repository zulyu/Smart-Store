package com.se300.ledger.model.assumptions;

import com.se300.ledger.SmartStoreApplication;
import com.se300.ledger.TestSmartStoreApplication;
import com.se300.ledger.model.*;
import com.se300.ledger.repository.AccountRepository;
import com.se300.ledger.service.Ledger;
import com.se300.ledger.service.LedgerAPI;
import com.se300.ledger.service.LedgerException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assumptions.assumeTrue;
import static org.junit.jupiter.api.Assumptions.assumingThat;

@SpringBootTest(classes = {TestSmartStoreApplication.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class AssumptionsTest {

    @Autowired
    Ledger ledger;
    Account mary;
    Account sergey;

    @BeforeAll
    void setUpClass() throws LedgerException {

        Account master = ledger.getUncommittedBlock().getAccount("master");

        mary = ledger.createAccount("mary");
        sergey = ledger.createAccount("sergey");

        Transaction firstTransaction =
                new Transaction("1",60,10,"simple test", master, mary);
        Transaction secondTransaction =
                new Transaction("2",60,10,"simple test", master, mary);
        Transaction thirdTransaction =
                new Transaction("3",60,10,"simple test", master, mary);
        Transaction forthTransaction =
                new Transaction("4",60,10,"simple test", master, mary);
        Transaction fifthTransaction =
                new Transaction("5",60,10,"simple test", master, mary);
        Transaction sixTransaction =
                new Transaction("6",60,10,"simple test", master, mary);
        Transaction seventhTransaction =
                new Transaction("7",60,10,"simple test", master, mary);
        Transaction eightsTransaction =
                new Transaction("8",60,10,"simple test", master, mary);
        Transaction ninthTransaction =
                new Transaction("9",60,10,"simple test", master, mary);
        Transaction tenthTransaction =
                new Transaction("10",60,10,"simple test", master, mary);


        ledger.processTransaction(firstTransaction);
        ledger.processTransaction(secondTransaction);
        ledger.processTransaction(thirdTransaction);
        ledger.processTransaction(forthTransaction);
        ledger.processTransaction(fifthTransaction);
        ledger.processTransaction(sixTransaction);
        ledger.processTransaction(seventhTransaction);
        ledger.processTransaction(eightsTransaction);
        ledger.processTransaction(ninthTransaction);
        ledger.processTransaction(tenthTransaction);

    }

    @BeforeEach
    void setUp() throws LedgerException {
        assumeTrue(ledger.getAccountBalance("master") > 0);
    }

    @Test
    void testTransaction(){

        assumingThat(ledger.getTransaction("11") == null,
                () -> {
                    Transaction firstTransaction =
                            new Transaction("11",60,10,"simple test", mary, sergey);
                    ledger.processTransaction(firstTransaction);
                });

    }
}
