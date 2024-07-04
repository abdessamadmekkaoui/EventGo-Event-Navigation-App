package com.example.avent_go.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.avent_go.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class Contactus extends AppCompatActivity {
    EditText editTextSubject, editTextContent;
    Button button;
    String fixedToEmail = "event01go@gmail.com"; // Adresse e-mail fixe

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contactus);

        //////////////////ADD//////////////
        FloatingActionButton addBtn = findViewById(R.id.addBtn);

        addBtn.setOnClickListener(view -> {
            // Open the 'activity_upload' when the FloatingActionButton is clicked
            Intent intent = new Intent(Contactus.this, UploadActivity.class);
            startActivity(intent);
        });
////////////////////ADD////////////////
        findViewById(R.id.homepa).setOnClickListener(v -> openNewActivity(home_page.class));
        findViewById(R.id.favorite).setOnClickListener(v -> openNewActivity(Favorite.class));
        findViewById(R.id.contactus).setOnClickListener(v -> openNewActivity(Contactus.class));
        findViewById(R.id.aboutus).setOnClickListener(v -> openNewActivity(Aboutus.class));
        button = findViewById(R.id.btnSend);
        editTextSubject = findViewById(R.id.editTextSubject);
        editTextContent = findViewById(R.id.editTextContent);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String subject, content;

                subject = editTextSubject.getText().toString();
                content = editTextContent.getText().toString();

                if (subject.isEmpty() || content.isEmpty()) {
                    Toast.makeText(Contactus.this, "Subject and content are required", Toast.LENGTH_SHORT).show();
                } else {
                    sendEmail(subject, content, fixedToEmail);
                }
            }
        });
    }
    private void openNewActivity(Class<?> destinationActivity) {
        Intent intent = new Intent(this, destinationActivity);
        startActivity(intent);
    }

    public void sendEmail(String subject, String content, String toEmail) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{toEmail});
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, content);
        intent.setType("message/rfc822");
        startActivity(Intent.createChooser(intent, "Choose email client:"));
    }
}
