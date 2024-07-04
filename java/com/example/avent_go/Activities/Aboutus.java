package com.example.avent_go.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.TextView;

import com.example.avent_go.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class Aboutus extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aboutus);
        findViewById(R.id.homepa).setOnClickListener(v -> openNewActivity(home_page.class));
        findViewById(R.id.favorite).setOnClickListener(v -> openNewActivity(Favorite.class));
        findViewById(R.id.contactus).setOnClickListener(v -> openNewActivity(Contactus.class));
        findViewById(R.id.aboutus).setOnClickListener(v -> openNewActivity(Aboutus.class));

        //////////////////ADD//////////////
        FloatingActionButton addBtn = findViewById(R.id.addBtn);

        addBtn.setOnClickListener(view -> {
            // Open the 'activity_upload' when the FloatingActionButton is clicked
            Intent intent = new Intent(Aboutus.this, UploadActivity.class);
            startActivity(intent);
        });
////////////////////ADD////////////////
    }

    public void openNewActivity(Class<?> destinationActivity) {
        Intent intent = new Intent(this, destinationActivity);
        startActivity(intent);
    }
    public void openFacebookPage(View view) {
        // Remplacez "your_facebook_page_id" par l'ID de votre page Facebook
        String facebookPageId = "profile.php?id=100090611448410&mibextid=ZbWKwL";
        String facebookUrl = "https://www.facebook.com/" + facebookPageId;

        Intent facebookIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(facebookUrl));
        startActivity(facebookIntent);

    }
    public void openinstaPage(View view) {
        // Remplacez "your_facebook_page_id" par l'ID de votre page Facebook
        String instagramPageId = "eventgo1?igsh=dGhpd2gwNWphbXAx";
        String instaUrl = "https://www.instagram.com/" + instagramPageId;

        Intent instaIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(instaUrl));
        startActivity(instaIntent);

    }
    public void openytbPage(View view) {
        String ytbPageId = "@Eventgo1";
        String ytbUrl = "https://www.youtube.com/" + ytbPageId;

        Intent ytbIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(ytbUrl));
        startActivity(ytbIntent);
    }
    public void opentelePage(View view) {
        String phoneNumber = "+212708551965";
        Intent callIntent = new Intent(Intent.ACTION_DIAL);
        callIntent.setData(Uri.parse("tel:" + phoneNumber));
        startActivity(callIntent);
    }

}
