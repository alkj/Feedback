package com.example.admin.feedback_app;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class StartScreenActivity extends AppCompatActivity {

    Button knap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        knap = findViewById(R.id.LoginButtonStartscreen);
    }
 }
