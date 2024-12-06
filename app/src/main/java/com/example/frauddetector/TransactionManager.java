package com.example.frauddetector;

import android.util.Log;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class TransactionManager {
    private HashMap<String, Transaction> transactionMap;
    private HashSet<String> duplicateTransactions;
    private HashSet<String> suspiciousTransactions;
    private HashMap<String, LinkedList<String>> accountGraph;
    private DatabaseReference database;

    public TransactionManager() {
        transactionMap = new HashMap<>();
        duplicateTransactions = new HashSet<>();
        suspiciousTransactions = new HashSet<>();
        accountGraph = new HashMap<>();
        database = FirebaseDatabase.getInstance().getReference("BankingSystem");
    }


    public void addTransaction(Transaction transaction) {
        if (transaction.getTransactionID() == null || transaction.getTransactionID().isEmpty()) {
            Log.e("TransactionManager", "Invalid Transaction ID: " + transaction.getTransactionID());
            return;
        }

        transactionMap.put(transaction.getTransactionID(), transaction);
        database.child("Transactions").child(transaction.getTransactionID()).setValue(transaction);
        addToGraph(transaction);
        checkForFraud(transaction);
    }


    public void clearData() {
        transactionMap.clear();
        duplicateTransactions.clear();
        suspiciousTransactions.clear();
        accountGraph.clear();
    }


    public HashSet<String> getDuplicateTransactions() {
        return duplicateTransactions;
    }

    public HashSet<String> getSuspiciousTransactions() {
        return suspiciousTransactions;
    }

    public HashMap<String, LinkedList<String>> getAccountGraph() {
        return accountGraph;
    }

    private void checkForFraud(Transaction transaction) {
        checkDuplicate(transaction);
        checkSuspiciousAmount(transaction);
        checkMultipleTransactionsInShortTime(transaction);
        checkRapidTransfers(transaction);
        checkUnusualLocation(transaction);
    }


    private void checkDuplicate(Transaction transaction) {
        for (Transaction existingTransaction : transactionMap.values()) {
            if (!existingTransaction.getTransactionID().equals(transaction.getTransactionID())
                    && existingTransaction.getAmount() == transaction.getAmount()
                    && existingTransaction.getTimestamp().equals(transaction.getTimestamp())) {
                duplicateTransactions.add(transaction.getTransactionID());
                break;
            }
        }
    }


    private void checkSuspiciousAmount(Transaction transaction) {
        if (transaction.getAmount() > 10000.00) {
            suspiciousTransactions.add(transaction.getTransactionID());
        }
    }



    private void checkMultipleTransactionsInShortTime(Transaction transaction) {
        long currentTime = System.currentTimeMillis();


        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        try {

            Date transactionDate = dateFormat.parse(transaction.getTimestamp());


            long transactionTimestamp = transactionDate.getTime();

            long timeDifference = currentTime - transactionTimestamp;

            if (timeDifference < 10 * 60 * 1000) { // 10 minutes
                suspiciousTransactions.add(transaction.getTransactionID());
            }
        } catch (ParseException e) {
            Log.e("TransactionManager", "Error parsing timestamp: " + transaction.getTimestamp(), e);
        }
    }



    private void checkRapidTransfers(Transaction transaction) {

    }

    private void checkUnusualLocation(Transaction transaction) {

    }

    // Adds a transaction to the graph
    private void addToGraph(Transaction transaction) {
        String sender = transaction.getAccountID();
        String receiver = transaction.getReceiverID();

        accountGraph.computeIfAbsent(sender, k -> new LinkedList<>()).add(receiver);
        accountGraph.computeIfAbsent(receiver, k -> new LinkedList<>()).add(sender);
    }

}
