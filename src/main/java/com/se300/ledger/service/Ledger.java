package com.se300.ledger.service;

import com.se300.ledger.model.*;
import com.se300.ledger.repository.AccountRepository;
import com.se300.ledger.repository.TransactionRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;
import static java.util.Map.*;

/**
 * Ledger Class representing simple implementation of Blockchain
 *
 * @author  Sergey L. Sundukovskiy
 * @version 1.0
 * @since   2023-10-11
 */

@RequiredArgsConstructor
public class Ledger implements LedgerAPI {

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    TransactionRepository transactionRepository;

    private String name;
    private String description;
    private String seed;
    private static NavigableMap <Integer, Block> blockMap = new TreeMap<>();
    private static Block uncommittedBlock = new Block(1, "");
    private static Ledger ledger;

    @PostConstruct
    private void createMasterAccount(){
        Account masterAccount = new Account("master", Integer.MAX_VALUE);
        uncommittedBlock.addAccount("master", masterAccount);
        this.accountRepository.save(masterAccount);
    }

    /**
     * Create singleton of the Ledger
     * @param name
     * @param description
     * @param seed
     * @return
     */
    public static synchronized Ledger getInstance(String name, String description,  String seed) {
        if (ledger == null) {
            blockMap = new TreeMap<>();
            ledger = new Ledger(name, description, seed);
        }
        return ledger;
    }

    /**
     * Private Ledger Constructor
     * @param name
     * @param description
     * @param seed
     */
    private Ledger(String name, String description, String seed) {
        this.name = name;
        this.description = description;
        this.seed = seed;
    }

    /**
     * Getter method for the name of the Ledger
     * @return
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * Setter Method for the name of the Ledger
     * @param name
     */
    @Override
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Getter Method for Ledger description
     * @return String
     */
    @Override
    public String getDescription() {
        return description;
    }

    /**
     * Setter Method for Description
     * @param description
     */
    @Override
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Getter Method for the seed
     * @return String
     */
    @Override
    public String getSeed() {
        return seed;
    }

    /**
     * Setter Method for the seed
     * @param seed
     */
    @Override
    public void setSeed(String seed) {
        this.seed = seed;
    }

    /**
     * Method for creating accounts in the blockchain
     * @param address
     * @return Account representing account in the Blockchain
     */
    @Override
    public Account createAccount(String address) throws LedgerException {

        if(uncommittedBlock.getAccount(address) != null){
            throw new LedgerException("Create Account", "Account Already Exists");
        }

        Account account = new Account(address, 0);

        uncommittedBlock.addAccount(address, account);

        this.accountRepository.save(account);

        return account;
    }

    /**
     * Method implementing core functionality of the Blockchain by handling given transaction
     * @param transaction
     * @return String representing transaction id
     * @throws LedgerException
     */
    @Override
    public synchronized String processTransaction(Transaction transaction) throws LedgerException {

        //Check for transaction specification conditions
        if(transaction.getAmount() < 0 || transaction.getAmount() > Integer.MAX_VALUE ){
            throw new LedgerException("Process Transaction", "Transaction Amount Is Out of Range");
        } else if (transaction.getFee() < 10) {
            throw new LedgerException("Process Transaction", "Transaction Fee Must Be Greater Than 10");
        } else if (transaction.getNote().length() > 1024){
            throw new LedgerException("Process Transaction", "Note Length Must Be Less Than 1024 Chars");
        }

        if(ledger.getTransaction(transaction.getTransactionId()) != null){
            throw new LedgerException("Process Transaction", "Transaction Id Must Be Unique");
        }

        Account tempPayerAccount = transaction.getPayer();
        Account tempReceiverAccount = transaction.getReceiver();

        if(transaction.getPayer().getBalance() < (transaction.getAmount() + transaction.getFee()))
            throw new LedgerException("Process Transaction", "Payer Does Not Have Required Funds");

        //Deduct balance of the payer
        tempPayerAccount.setBalance(tempPayerAccount.getBalance()
                - transaction.getAmount() - transaction.getFee());
        //Increase balance of the receiver
        tempReceiverAccount.setBalance(tempReceiverAccount.getBalance() + transaction.getAmount());

        uncommittedBlock.getTransactionList().add(transaction);

        //TD: Persist Transaction to the DB
        this.transactionRepository.save(transaction);

        //Check to see if account blocked has reached max size
        if (uncommittedBlock.getTransactionList().size() == 10){

            List<String> tempTxList = new ArrayList<>();
            tempTxList.add(seed);

            //Loop through the list of transaction to get the hash
            for( Transaction tempTx : uncommittedBlock.getTransactionList()){
                tempTxList.add(tempTx.toString());
            }

            MerkleTrees merkleTrees = new MerkleTrees(tempTxList);
            merkleTrees.merkle_tree();
            uncommittedBlock.setHash(merkleTrees.getRoot());

            //Commit uncommitted block
            blockMap.put(uncommittedBlock.getBlockNumber(), uncommittedBlock);

            //Get committed block
            Block committedBlock = blockMap.lastEntry().getValue();
            Map<String,Account> accountMap = committedBlock.getAccountBalanceMap();

            //Get all the accounts
            List<Account> accountList = new ArrayList<Account>(accountMap.values());

            //Create next block
            uncommittedBlock = new Block(uncommittedBlock.getBlockNumber() + 1,
                    committedBlock.getHash());

            //Replicate accounts
            for (Account account : accountList) {
                Account tempAccount = (Account) account.clone();
                uncommittedBlock.addAccount(tempAccount.getAddress(), tempAccount);
            }

            //Link to previous block
            uncommittedBlock.setPreviousBlock(committedBlock);
        }

        return transaction.getTransactionId();
    }

    /**
     * Get Account balance by address
     * @param address
     * @return Integer representing balance of the Account
     * @throws LedgerException
     */
    @Override
    public Integer getAccountBalance(String address) throws LedgerException {

        if(blockMap.isEmpty()){
            throw new LedgerException("Get Account Balance", "Account Is Not Committed to a Block");
        }

        Block block = blockMap.lastEntry().getValue();
        Account account = block.getAccount(address);

        if (account == null)
            throw new LedgerException("Get Account Balance", "Account Does Not Exist");
        else
            return account.getBalance();
    }

    /**
     * Get all Account balances that are part of the Blockchain
     * @return Map representing Accounts and balances
     */
    @Override
    public Map<String,Integer> getAccountBalances(){

        if(blockMap.isEmpty())
            return null;

        Block committedBlock = blockMap.lastEntry().getValue();
        Map<String,Account> accountMap = committedBlock.getAccountBalanceMap();

        Map<String, Integer> balances = new HashMap<>();
        List<Account> accountList = new ArrayList<>(accountMap.values());

        for (Account account : accountList) {
            balances.put(account.getAddress(), account.getBalance());
        }

        return balances;
    }

    /**
     * Get Block by id
     * @param blockNumber
     * @return Block or Null
     */
    @Override
    public Block getBlock(Integer blockNumber) throws LedgerException {
        Block block = blockMap.get(blockNumber);
        if(block == null){
            throw new LedgerException("Get Block", "Block Does Not Exist");
        }
        return block;
    }

    /**
     * Get Transaction by id
     * @param transactionId
     * @return Transaction or Null
     */
    @Override
    public Transaction getTransaction(String transactionId){

        //TD: Refactor to Retrieve Transaction fom the DB
            Optional<Transaction> optionalTransaction = transactionRepository.findById(transactionId);

            if (optionalTransaction.isPresent()) {
                return optionalTransaction.get();
            }

            // If the transaction is not found in committed blocks
            for (Entry<Integer, Block> mapElement : blockMap.entrySet()) {
                Block tempBlock = mapElement.getValue();
                for (Transaction transaction : tempBlock.getTransactionList()) {
                    if (transaction.getTransactionId().equals(transactionId)) {
                        return transaction;
                    }
                }
            }

            // If the transaction is not found in the uncommitted block
            for (Transaction transaction : uncommittedBlock.getTransactionList()) {
                if (transaction.getTransactionId().equals(transactionId)) {
                    return transaction;
                }
            }

            return null;
        }


    /**
     * Get number of Blocks in the Blockchain
     * @return int representing number of blocks committed to Blockchain
     */
    @Override
    public int getNumberOfBlocks(){
        return blockMap.size();
    }

    /**
     * Method for validating Blockchain.
     * Check each block for Hash consistency
     * Check each block for Transaction count
     * Check account balances against the total
     */
    @Override
    public void validate() throws LedgerException {

        Block committedBlock = blockMap.lastEntry().getValue();
        Map<String,Account> accountMap = committedBlock.getAccountBalanceMap();
        List<Account> accountList = new ArrayList<>(accountMap.values());

        int totalBalance = 0;
        for (Account account : accountList) {
            totalBalance += account.getBalance();
        }

        int fees = 0;
        String hash;
        for(Integer key : blockMap.keySet()){
            Block block = blockMap.get(key);

            //Check for Hash Consistency
            if(block.getBlockNumber() != 1)
                if(!block.getPreviousHash().equals(block.getPreviousBlock().getHash())){
                    throw new LedgerException("Validate", "Hash Is Inconsistent: "
                            + block.getBlockNumber());
            }

            //Check for Transaction Count
            if(block.getTransactionList().size() != 10){
                throw new LedgerException("Validate", "Transaction Count Is Not 10 In Block: "
                        + block.getBlockNumber());
            }

            for(Transaction transaction : block.getTransactionList()){
                fees += transaction.getFee();
            }
        }

        int adjustedBalance = totalBalance + fees;

        //Check for account balances against the total
        if(adjustedBalance != Integer.MAX_VALUE){
            throw new LedgerException("Validate", "Balance Does Not Add Up");
        }

    }

    /**
     * Helper method for CommandProcessor
     * @return current block we are working with
     */
    @Override
    public Block getUncommittedBlock(){
        return uncommittedBlock;
    }



}


