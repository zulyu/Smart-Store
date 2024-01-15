package com.se300.ledger.service;


import com.se300.ledger.TestSmartStoreApplication;
import com.se300.ledger.model.Account;
import com.se300.ledger.model.Transaction;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = {TestSmartStoreApplication.class})
public class LedgerIntegrationTest {

    @Autowired
    private Ledger ledger;

    @Test
    public void testCompleteLedger() throws LedgerException {

        Account master = ledger.getUncommittedBlock().getAccount("master");
        Account mary = ledger.createAccount("mary");

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

        assertEquals(600, mary.getBalance());
    }

    //TD Create Another Ledger Integration Test
    @Test
    void testLedgerIntegration() throws LedgerException {
        Account master = ledger.getUncommittedBlock().getAccount("master");
        Account john = ledger.createAccount("john");

        Transaction eleventhTransaction =
                new Transaction("11", 50, 20, "another test", master, john);
        Transaction twelfthTransaction =
                new Transaction("12", 30, 15, "another test", master, john);

        ledger.processTransaction(eleventhTransaction);
        ledger.processTransaction(twelfthTransaction);

        assertEquals(80, john.getBalance());
    }
}
