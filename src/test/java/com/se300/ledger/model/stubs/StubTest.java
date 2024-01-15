package com.se300.ledger.model.stubs;

import com.se300.ledger.TestSmartStoreApplication;
import com.se300.ledger.model.*;
import com.se300.ledger.service.Ledger;
import com.se300.ledger.service.LedgerException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = {TestSmartStoreApplication.class})
public class StubTest {

    @Autowired
    Ledger ledger;
    @Test
    void testNegativeAmountValue() throws LedgerException {

        Transaction sampleTransaction = mock(Transaction.class);
        when(sampleTransaction.getAmount()).thenReturn(-1);

        assertThrows(LedgerException.class, () -> ledger.processTransaction(sampleTransaction));
    }
}
