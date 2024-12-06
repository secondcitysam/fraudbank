package com.example.frauddetector.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.frauddetector.Transaction;
import com.example.frauddetector.TransactionManager;
import com.example.frauddetector.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
public class fraud extends Fragment {

    private DatabaseReference databaseReference;
    private TransactionManager transactionManager;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> fraudReports;
    private static final double SUSPICIOUS_AMOUNT_THRESHOLD = 10000.00;
    private static final long SHORT_TIME_FRAME = 10 * 60 * 1000;
    private static final double LOCATION_THRESHOLD = 100.0;

    private Map<String, Long> lastTransactionTime = new HashMap<>();
    private Map<String, String> lastTransactionLocation = new HashMap<>();
    private Map<String, Integer> transactionCount = new HashMap<>();

    public fraud() {

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_fraud, container, false);


        fraudReports = new ArrayList<>();
        ListView fraudListView = v.findViewById(R.id.fraudListView);
        adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_list_item_1, fraudReports);
        fraudListView.setAdapter(adapter);


        databaseReference = FirebaseDatabase.getInstance().getReference("BankingSystem").child("Transactions");
        transactionManager = new TransactionManager();


        fetchTransactionsFromDatabase();

        return v;
    }

    private void fetchTransactionsFromDatabase() {
        databaseReference.addValueEventListener(new com.google.firebase.database.ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                transactionManager.clearData();

                for (DataSnapshot data : snapshot.getChildren()) {

                    if (data.getValue() != null) {

                        Transaction transaction = data.getValue(Transaction.class);
                        if (transaction != null) {

                            transactionManager.addTransaction(transaction);


                            checkForSuspiciousTransaction(transaction);
                            checkForMultipleTransactionsInShortTime(transaction);
                            //checkForUnusualLocation(transaction);
                            checkForRapidTransfers(transaction);
                        }
                    }
                }


                updateFraudReports();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                Toast.makeText(getContext(), "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void checkForSuspiciousTransaction(Transaction transaction) {

        if (transaction.getAmount() > SUSPICIOUS_AMOUNT_THRESHOLD) {
            String report = "Suspicious transaction detected: \n" +
                    "Transaction ID: " + transaction.getTransactionID() + "\n" +
                    "Amount: " + transaction.getAmount() + "\n" +
                    "Account ID: " + transaction.getAccountID() + "\n" +
                    "Reason: Amount above suspicious threshold.";
            fraudReports.add(report);
        }
    }

    private void checkForMultipleTransactionsInShortTime(Transaction transaction) {

        String accountID = transaction.getAccountID();
        long currentTime = System.currentTimeMillis();
        Long lastTime = lastTransactionTime.get(accountID);


        if (lastTime != null && currentTime - lastTime < SHORT_TIME_FRAME) {
            String report = "Multiple transactions in short time detected: \n" +
                    "Transaction ID: " + transaction.getTransactionID() + "\n" +
                    "Account ID: " + accountID + "\n" +
                    "Reason: Multiple transactions within a short time.";
            fraudReports.add(report);
        }


        lastTransactionTime.put(accountID, currentTime);
    }

    private void checkForUnusualLocation(Transaction transaction) {

        String accountID = transaction.getAccountID();
        String currentLocation = transaction.getLocation();
        String lastLocation = lastTransactionLocation.get(accountID);


        if (lastLocation != null && !lastLocation.equals(currentLocation)) {
            double distance = calculateDistance(lastLocation, currentLocation);
            if (distance > LOCATION_THRESHOLD) {
                String report = "Unusual location detected: \n" +
                        "Transaction ID: " + transaction.getTransactionID() + "\n" +
                        "Account ID: " + accountID + "\n" +
                        "Distance: " + distance + " km\n" +
                        "Reason: Location change exceeds the threshold.";
                fraudReports.add(report);
            }
        }


        lastTransactionLocation.put(accountID, currentLocation);
    }

    private void checkForRapidTransfers(Transaction transaction) {

        String accountID = transaction.getAccountID();
        long currentTime = System.currentTimeMillis();
        Integer count = transactionCount.getOrDefault(accountID, 0);


        if (count >= 3 && currentTime - lastTransactionTime.get(accountID) < SHORT_TIME_FRAME) {
            String report = "Rapid transfers detected: \n" +
                    "Account ID: " + accountID + "\n" +
                    "Reason: Several transactions within a short time frame.";
            fraudReports.add(report);
        }


        transactionCount.put(accountID, count + 1);
    }

    private double calculateDistance(String lastLocation, String currentLocation) {

        return Math.random() * 150; // Random distance in km
    }

    private void updateFraudReports() {

        adapter.notifyDataSetChanged();
    }
}
