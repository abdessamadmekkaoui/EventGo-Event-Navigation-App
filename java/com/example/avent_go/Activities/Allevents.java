package com.example.avent_go.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.avent_go.Adapters.AllAdapter;
import com.example.avent_go.Domains.PopularDomain;
import com.example.avent_go.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
public class Allevents extends AppCompatActivity {
    private RecyclerView.Adapter adapterAll;
    private ArrayList<PopularDomain> items;
    private RecyclerView recyclerViewAll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_allevents);


        //////////////////ADD//////////////
        FloatingActionButton addBtn = findViewById(R.id.addBtn);

        addBtn.setOnClickListener(view -> {
            // Open the 'activity_upload' when the FloatingActionButton is clicked
            Intent intent = new Intent(Allevents.this, UploadActivity.class);
            startActivity(intent);
        });
////////////////////ADD////////////////

        // Retrieve the items array from the intent
        items = (ArrayList<PopularDomain>) getIntent().getSerializableExtra("items");
            ArrayList<PopularDomain> favoriteItems = getFavoriteItems(items);
            initRecyclerViews(favoriteItems);
        findViewById(R.id.homepa).setOnClickListener(v -> openNewActivity(home_page.class));
        findViewById(R.id.favorite).setOnClickListener(v -> openNewActivity(Favorite.class));
        findViewById(R.id.contactus).setOnClickListener(v -> openNewActivity(Contactus.class));
        findViewById(R.id.aboutus).setOnClickListener(v -> openNewActivity(Aboutus.class));
    }

    private void openNewActivity(Class<?> destinationActivity) {
        Intent intent = new Intent(this, destinationActivity);
        intent.putExtra("items", items);
        startActivity(intent);
    }

    private void initRecyclerViews(ArrayList<PopularDomain> displayItems) {
        recyclerViewAll = findViewById(R.id.view_all);
        recyclerViewAll.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        adapterAll = new AllAdapter(displayItems);
        recyclerViewAll.setAdapter(adapterAll);
    }

    private ArrayList<PopularDomain> getFavoriteItems(ArrayList<PopularDomain> allItems) {
        ArrayList<PopularDomain> favoriteItems = new ArrayList<>();
        for (PopularDomain item : allItems) {
            if (1==1) {
                favoriteItems.add(item);
            }
        }
        return favoriteItems;
    }
}
