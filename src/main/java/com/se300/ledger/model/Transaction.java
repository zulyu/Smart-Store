package com.se300.ledger.model;

import jakarta.persistence.*;

import java.util.Objects;

/**
 * Transaction class implementation representing operation in the Blockchain
 *
 * @author  Sergey L. Sundukovskiy
 * @version 1.0
 * @since   2023-10-11
 */
@Entity
public class Transaction {

    @Id
    private String transactionId;
    @Column
    private Integer amount;
    @Column
    private Integer fee;
    @Column
    private String note;
    @ManyToOne
    @JoinColumn(name = "payer_id")
    private Account payer;
    @ManyToOne
    @JoinColumn(name = "receiver_id")
    private Account receiver;

    public Transaction() {
    }

    /**
     * Constructor for Transaction
     * @param transactionId
     * @param amount
     * @param fee
     * @param note
     * @param payer
     * @param receiver
     */
    public Transaction(String transactionId, Integer amount, Integer fee, String note, Account payer, Account receiver) {
        this.transactionId = transactionId;
        this.amount = amount;
        this.fee = fee;
        this.note = note;
        this.payer = payer;
        this.receiver = receiver;
    }

    /**
     * Getter method for transaction id
     * @return
     */
    public String getTransactionId() {
        return transactionId;
    }

    /**
     * Setter method for transaction id
     * @param transactionId
     */
    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    /**
     * Getter method for transaction amount
     * @return
     */
    public Integer getAmount() {
        return amount;
    }

    /**
     * Setter method for transaction amount
     * @param amount
     */
    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    /**
     * Getter method for transaction fee
     * @return
     */
    public Integer getFee() {
        return fee;
    }

    /**
     * Setter method for transaction fee
     * @param fee
     */
    public void setFee(Integer fee) {
        this.fee = fee;
    }

    /**
     * Getter method for transaction note
     * @return
     */
    public String getNote() {
        return note;
    }

    /**
     * Setter method for transaction note
     * @param note
     */
    public void setNote(String note) {
        this.note = note;
    }

    /**
     * Getter method for payer
     * @return
     */
    public Account getPayer() {
        return payer;
    }

    /**
     * Setter method for payer
     * @param payer
     */
    public void setPayer(Account payer) {
        this.payer = payer;
    }

    /**
     * Getter method for receiver
     * @return
     */
    public Account getReceiver() {
        return receiver;
    }

    /**
     * Setter method for receiver
     * @param receiver
     */
    public void setReceiver(Account receiver) {
        this.receiver = receiver;
    }

    /**
     * Method used by MerkleTrees
     * @return
     */
    @Override
    public String toString() {
        return "Transaction Id: " + transactionId +
                ", Amount: " + amount +
                ", Fee: " + fee +
                ", Note: " + note +
                ", Payer: " + payer.getAddress() +
                ", Receiver: " + receiver.getAddress();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Transaction)) return false;
        Transaction that = (Transaction) o;
        return getTransactionId().equals(that.getTransactionId()) && getAmount().equals(that.getAmount()) && getFee().equals(that.getFee()) && getNote().equals(that.getNote()) && getPayer().equals(that.getPayer()) && getReceiver().equals(that.getReceiver());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTransactionId(), getAmount(), getFee(), getNote(), getPayer(), getReceiver());
    }
}

