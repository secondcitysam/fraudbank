package com.example.frauddetector;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private TextView title , tag;


    Animation splash,splash2;
    FirebaseAuth mAuth ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();


        title = findViewById(R.id.title);
        tag = findViewById(R.id.tag);

        splash = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.anim);
        splash2 = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.anim2);

        title.setAnimation(splash);
        tag.setAnimation(splash2);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(getApplicationContext(),Home.class));

            }
        },3500);
    }
}