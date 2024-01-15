package com.se300.ledger.service;

import com.se300.ledger.model.Account;
import com.se300.ledger.model.Block;
import com.se300.ledger.model.Transaction;

import java.util.Map;

public interface LedgerAPI {


    String getName();

    void setName(String name);

    String getDescription();

    void setDescription(String description);

    String getSeed();

    void setSeed(String seed);

    Account createAccount(String address) throws LedgerException;

    String processTransaction(Transaction transaction) throws LedgerException;

    Integer getAccountBalance(String address) throws LedgerException;

    Map<String, Integer> getAccountBalances();

    Block getBlock(Integer blockNumber) throws LedgerException;

    Transaction getTransaction(String transactionId);

    int getNumberOfBlocks();

    void validate() throws LedgerException;

    Block getUncommittedBlock();
}
