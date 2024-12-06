package com.example.frauddetector.fragments;

import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.frauddetector.R;
import com.example.frauddetector.Transaction;
import com.example.frauddetector.TransactionManager;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link add#newInstance} factory method to
 * create an instance of this fragment.
 */
public class add extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public add() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment add.
     */
    // TODO: Rename and change types and number of parameters
    public static add newInstance(String param1, String param2) {
        add fragment = new add();
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
    private TransactionManager transactionManager;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
      View v = inflater.inflate(R.layout.fragment_add, container, false);


        transactionManager = new TransactionManager();

        EditText transactionIdInput = v.findViewById(R.id.tid);
        EditText accountIdInput = v.findViewById(R.id.accid);
        EditText locationInput = v.findViewById(R.id.loc);
        EditText amountInput = v.findViewById(R.id.amount);
        AppCompatButton appCompatButton = v.findViewById(R.id.addbtn);

        appCompatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String transactionID = transactionIdInput.getText().toString();
                    String accountID = accountIdInput.getText().toString();
                    String location = locationInput.getText().toString();
                    double amount = Double.parseDouble(amountInput.getText().toString());


                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    sdf.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
                    String timestamp = sdf.format(new Date());


                    Transaction transaction = new Transaction(transactionID, accountID, location, amount, timestamp);


                    transactionManager.addTransaction(transaction);


                    Toast.makeText(getActivity(), "Transaction Added Successfully!", Toast.LENGTH_SHORT).show();


                    transactionIdInput.setText("");
                    accountIdInput.setText("");
                    locationInput.setText("");
                    amountInput.setText("");
                } catch (Exception e) {
                    Toast.makeText(getActivity(), "Error adding transaction: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
        return    v;
    }
}