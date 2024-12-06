package com.example.frauddetector;
public class Transaction {
    private String transactionID;
    private String accountID;
    private double amount;
    private String timestamp;
    private String location;
    private String receiverID;

    public Transaction() {
    }



    public Transaction(String transactionID, String accountID, String location, double amount, String timestamp) {
        this.transactionID = transactionID;
        this.accountID = accountID;
        this.amount = amount;
        this.timestamp = timestamp;
        this.location = location;
    }

    public String getTransactionID() {
        return transactionID;
    }

    public String getAccountID() {
        return accountID;
    }

    public double getAmount() {
        return amount;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public String getLocation() {
        return location;
    }

    public String getReceiverID() {
        return receiverID;
    }
}
