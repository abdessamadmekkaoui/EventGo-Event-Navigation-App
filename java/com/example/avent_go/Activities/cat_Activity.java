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
public class cat_Activity extends AppCompatActivity {
    private RecyclerView.Adapter adapterAll;
    private ArrayList<PopularDomain> items;
    private RecyclerView recyclerViewAll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);


        //////////////////ADD//////////////
        FloatingActionButton addBtn = findViewById(R.id.addBtn);

        addBtn.setOnClickListener(view -> {
            // Open the 'activity_upload' when the FloatingActionButton is clicked
            Intent intent = new Intent(cat_Activity.this, UploadActivity.class);
            startActivity(intent);
        });
////////////////////ADD////////////////
        // Retrieve the items array from the intent
        items = (ArrayList<PopularDomain>) getIntent().getSerializableExtra("items");
        if (items != null && !items.isEmpty()) {
            // Filter items to show only those with isFavorite = true
            ArrayList<PopularDomain> favoriteItems = getFavoriteItems(items);
            initRecyclerViews(favoriteItems);
        } else {
            // Handle the case when items is null
            Toast.makeText(this, "Items list is null", Toast.LENGTH_SHORT).show();
            // Finish or navigate to a default place
            finish();
        }
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
        recyclerViewAll = findViewById(R.id.view_al);
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
