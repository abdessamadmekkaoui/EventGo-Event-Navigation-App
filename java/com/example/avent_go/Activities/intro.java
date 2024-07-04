package com.example.avent_go.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.avent_go.R;

public class intro extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        ConstraintLayout introBtn=findViewById(R.id.introBtn);
        introBtn.setOnClickListener(v -> startActivity(new Intent(intro.this, home_page.class)));

        TextView loginTextView = findViewById(R.id.logInt);

        loginTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(intro.this, login.class));
            }
        });

    }
}