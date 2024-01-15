package com.se300.ledger.model;

import com.se300.ledger.SmartStoreApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest(classes = {SmartStoreApplication.class})
public class TransactionTest {

    //TD: Implement Transaction Test
    @Test
    public void testSetTransactionId() {
        // Arrange
        Transaction transaction = new Transaction();
        String expectedTransactionId = "testTransactionId";

        // Act
        transaction.setTransactionId(expectedTransactionId);

        // Assert
        String actualTransactionId = transaction.getTransactionId();
        assertEquals(expectedTransactionId, actualTransactionId, "TransactionId should be set correctly");
    }

    @Test
    public void testSetAmount() {
        // Arrange
        Transaction transaction = new Transaction();
        Integer amount = 100;

        // Act
        transaction.setAmount(amount);

        // Assert
        assertEquals(amount, transaction.getAmount(), "Amount should be set correctly");
    }

    @Test
    public void testSetFee() {
        // Arrange
        Transaction transaction = new Transaction();
        Integer fee = 20;

        // Act
        transaction.setFee(fee);

        // Assert
        assertEquals(fee, transaction.getFee(), "Fee should be set correctly");
    }

    @Test
    public void testSetNote() {
        // Arrange
        Transaction transaction = new Transaction();
        String note = "Test Note";

        // Act
        transaction.setNote(note);

        // Assert
        assertEquals(note, transaction.getNote(), "Note should be set correctly");
    }

        @Test
        public void testSetPayer() {
            // Arrange
            Transaction transaction = new Transaction();
            Account payer = new Account("payerAddress", 100);

            // Act
            transaction.setPayer(payer);

            // Assert
            assertEquals(payer, transaction.getPayer(), "Payer should be set correctly");
        }

    @Test
    public void testSetReceiver() {
        // Arrange
        Transaction transaction = new Transaction();
        Account receiver = new Account("receiverAddress", 200);

        // Act
        transaction.setReceiver(receiver);

        // Assert
        assertEquals(receiver, transaction.getReceiver(), "Receiver should be set correctly");
    }


}


