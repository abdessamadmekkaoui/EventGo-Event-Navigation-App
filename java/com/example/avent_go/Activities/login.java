package com.example.avent_go.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.avent_go.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class login extends AppCompatActivity {

    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://event-go-ca6bf-default-rtdb.firebaseio.com/");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final EditText phone = findViewById(R.id.phone);
        final EditText password = findViewById(R.id.password);
        final Button loginBtn = findViewById(R.id.loginBtn);
        final TextView registerNowBtn = findViewById(R.id.registerNowBtn);

        // Assuming you have already initialized your button
        AppCompatButton returnIntroBtn = findViewById(R.id.returnIntroBtn);

        // Set OnClickListener for the button
        returnIntroBtn.setOnClickListener(view -> {
            // Open IntroActivity when the button is clicked
            Intent intent = new Intent(login.this, intro.class);
            startActivity(intent);
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phoneTxt = phone.getText().toString();
                String passwordTxt = password.getText().toString();

                if (phoneTxt.isEmpty() || passwordTxt.isEmpty()) {
                    Toast.makeText(login.this, "Please enter your mobile or password", Toast.LENGTH_SHORT).show();
                } else {
                    databaseReference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.hasChild(phoneTxt)) {
                                String getPassword = snapshot.child(phoneTxt).child("password").getValue(String.class);
                                // Inside your login activity
                                if (getPassword.equals(passwordTxt)) {
                                    Toast.makeText(login.this, "Successfully Logged in", Toast.LENGTH_SHORT).show();
                                    String fullName = snapshot.child(phoneTxt).child("fullname").getValue(String.class);
                                    Intent intent = new Intent(login.this, home_page.class);
                                    intent.putExtra("user_email", phoneTxt);
                                    intent.putExtra("user_fullname", fullName);
                                    startActivity(intent);

                                    finish();
                                } else {
                                    Toast.makeText(login.this, "Wrong Password", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(login.this, "User not found", Toast.LENGTH_SHORT).show();
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                        }
                    });
                }
            }
        });
        registerNowBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(login.this, Register.class));
            }
        });
    }
}