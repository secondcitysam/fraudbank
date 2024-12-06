package com.example.frauddetector.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.frauddetector.R;
import com.example.frauddetector.Transaction;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link view#newInstance} factory method to
 * create an instance of this fragment.
 */
public class view extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public view() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment view.
     */
    // TODO: Rename and change types and number of parameters
    public static view newInstance(String param1, String param2) {
        view fragment = new view();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    private DatabaseReference databaseReference;
    private ArrayList<String> transactionList;
    private ArrayAdapter<String> adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View v= inflater.inflate(R.layout.fragment_view, container, false);

        transactionList = new ArrayList<>();
        ListView transactionListView = v.findViewById(R.id.transactionListView);


        adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, transactionList);
        transactionListView.setAdapter(adapter);


        databaseReference = FirebaseDatabase.getInstance().getReference("BankingSystem").child("Transactions");


        fetchTransactions();
       return v;

    }


    private void fetchTransactions() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                transactionList.clear();
                for (DataSnapshot data : snapshot.getChildren()) {
                    try {
                        Transaction transaction = data.getValue(Transaction.class);
                        if (transaction != null) {
                            String transactionDetails = "ID: " + transaction.getTransactionID() +
                                    " | Amount: Rs" + transaction.getAmount() +
                                    " | Date: " + transaction.getTimestamp();
                            transactionList.add(transactionDetails);
                        }
                    } catch (DatabaseException e) {
                        Toast.makeText(getContext(), "Invalid data format", Toast.LENGTH_SHORT).show();
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}