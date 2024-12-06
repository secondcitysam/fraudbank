package com.example.frauddetector;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;

import com.example.frauddetector.fragments.add;
import com.example.frauddetector.fragments.fraud;
import com.example.frauddetector.fragments.view;
import com.google.firebase.auth.FirebaseAuth;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;

public class Home extends AppCompatActivity {

    private FirebaseAuth mAuth;

    private ChipNavigationBar cnb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mAuth = FirebaseAuth.getInstance();

        cnb = findViewById(R.id.cnb);

        cnb.setItemSelected(R.id.view,true);

        getSupportFragmentManager().beginTransaction().replace(R.id.frame,new view()).commit();


        bottomMenu();
    }

    private void bottomMenu() {

        cnb.setOnItemSelectedListener(new ChipNavigationBar.OnItemSelectedListener() {
            @Override
            public void onItemSelected(int i) {
                Fragment fragment = null;

                if(i==R.id.view)
                {
                    fragment =new view();

                }
                else if(i==R.id.add)
                {
                    fragment =new add();

                } else if (i==R.id.record) {

                    fragment = new fraud();

                }

                getSupportFragmentManager().beginTransaction().replace(R.id.frame,fragment).commit();


            }
        });
    }
}