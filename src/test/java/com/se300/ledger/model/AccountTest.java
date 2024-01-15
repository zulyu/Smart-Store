package com.se300.ledger.model;

import com.se300.ledger.SmartStoreApplication;
import com.se300.ledger.TestSmartStoreApplication;
import com.se300.ledger.service.LedgerException;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = {SmartStoreApplication.class})
public class AccountTest {


    @Test
    void testAccountInstantiation() throws LedgerException {

        Account dummyAccount = new Account("sergey", 0);

        assertAll("Verify Account properties",
                () -> assertEquals("sergey", dummyAccount.getAddress()),
                () -> assertEquals(0, dummyAccount.getBalance()));

    }

    @Test
    public void testHashCode() {
        // Arrange
        String address = "testAddress";
        int balance = 4000;
        Account account = new Account(address, balance);

        // Act
        int hashCode = account.hashCode();

        // Assert
        int expectedHashCode = 31 * address.hashCode() + Integer.hashCode(balance) + 961;
        assertEquals(expectedHashCode, hashCode, "Hash code should match the expected value");

        // Print hash codes for debugging
        System.out.println("Expected Hash Code: " + expectedHashCode);
        System.out.println("Actual Hash Code: " + hashCode);
    }
}
