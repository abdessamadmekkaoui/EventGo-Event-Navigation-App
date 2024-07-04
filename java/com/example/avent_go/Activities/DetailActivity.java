package com.example.avent_go.Activities;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.avent_go.Domains.PopularDomain;
import com.example.avent_go.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class DetailActivity extends AppCompatActivity {

    private TextView titleTxt, locationTxt,priceTxt, placeTxt, eatTxt, wifiTxt, descriptionTxt, scoreTxt;
    private PopularDomain item;
    private ImageView picImg, backBtn, favoriteButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        //////////////////ADD//////////////
        FloatingActionButton addBtn = findViewById(R.id.addBtn);

        addBtn.setOnClickListener(view -> {
            // Open the 'activity_upload' when the FloatingActionButton is clicked
            Intent intent = new Intent(DetailActivity.this, UploadActivity.class);
            startActivity(intent);
        });
////////////////////ADD////////////////
        findViewById(R.id.homepa).setOnClickListener(v -> openNewActivity(home_page.class));
        findViewById(R.id.favorite).setOnClickListener(v -> openNewActivity(Favorite.class));
        findViewById(R.id.contactus).setOnClickListener(v -> openNewActivity(Contactus.class));
        findViewById(R.id.aboutus).setOnClickListener(v -> openNewActivity(Aboutus.class));

        initView();
        setVariable();
        handleFavoriteButtonClick();
    }
    public void openNewActivity(Class<?> destinationActivity) {
        Intent intent = new Intent(this, destinationActivity);
        startActivity(intent);
    }

    private void handleFavoriteButtonClick() {
        favoriteButton.setOnClickListener(v -> {
            item.setFavorite(!item.isFavorite());

            int favoriteIcon = item.isFavorite() ? R.drawable.favorite : R.drawable.love;
            favoriteButton.setImageResource(favoriteIcon);
        });
    }

    private void setVariable() {
        item = (PopularDomain) getIntent().getSerializableExtra("object");
        findViewById(R.id.bookbutton).setOnClickListener(v -> openWebsite(item.getUrl()));
        locationTxt.setOnClickListener(v -> openGoogleMaps(item.getLocation()));
        titleTxt.setText(item.getTitle());
        scoreTxt.setText(String.valueOf(item.getScore()));
        locationTxt.setText(item.getLocation());
        placeTxt.setText(String.valueOf(item.getPlace()));
        descriptionTxt.setText(item.getDescription());
        priceTxt.setText(String.valueOf(item.getPrice()));

        if (item.isGuide()) {
            eatTxt.setText("eat");
        } else {
            eatTxt.setText("No-eat");
        }

        if (item.isWifi()) {
            wifiTxt.setText("Wifi");
        } else {
            wifiTxt.setText("No-Wifi");
        }

        int drawableResId = getResources().getIdentifier(item.getPic(), "drawable", getPackageName());
        Glide.with(this)
                .load(drawableResId)
                .into(picImg);

        backBtn.setOnClickListener(v -> finish());

    }
    private void openGoogleMaps(String location) {
        // Create a Uri with the geo coordinates and the label
        Uri gmmIntentUri = Uri.parse("geo:0,0?q=" + Uri.encode(location));

        // Create an Intent to open Google Maps
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");

        // Verify that the Google Maps app is installed before launching the intent
        if (mapIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(mapIntent);
        } else {
            Toast.makeText(this, "Google Maps app not installed", Toast.LENGTH_SHORT).show();
        }
    }

    private void openWebsite(String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        startActivity(intent);
    }
    private void initView() {
        titleTxt = findViewById(R.id.titleTxt);
        locationTxt = findViewById(R.id.locationTxt);
        placeTxt = findViewById(R.id.bedTxt);
        priceTxt = findViewById(R.id.priceTxt);
        eatTxt = findViewById(R.id.guidTxt);
        wifiTxt = findViewById(R.id.wifiTxt);
        descriptionTxt = findViewById(R.id.descriptionTxt);
        scoreTxt = findViewById(R.id.scoreTxt);
        picImg = findViewById(R.id.picImg);
        backBtn = findViewById(R.id.backBtn);
        favoriteButton = findViewById(R.id.favoriteButt);
    }
}
