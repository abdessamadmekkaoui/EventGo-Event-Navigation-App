package com.example.avent_go.Activities;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.avent_go.Adapters.AllAdapter;
import com.example.avent_go.Adapters.CategoryAdapter;
import com.example.avent_go.Adapters.PupolarAdapter;
import com.example.avent_go.Domains.CategoryDomain;
import com.example.avent_go.Domains.PopularDomain;
import com.example.avent_go.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


import java.util.ArrayList;

public class home_page extends AppCompatActivity {
    private RecyclerView.Adapter adapterPopular, adapterCat, adapterAll;
    private SearchView searchView;
    private ArrayList<PopularDomain> items;
    private RecyclerView recyclerViewPopular, recyclerViewCategory, recyclerViewAll;

    private FirebaseAuth auth;
    private Button button;
    private TextView textView;
    private FirebaseUser user;

    private void openNewActivity(Class<?> destinationActivity) {
        Intent intent = new Intent(this, destinationActivity);
        intent.putExtra("items", items);
        startActivity(intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);


//////////////////ADD//////////////
        FloatingActionButton addBtn = findViewById(R.id.addBtn);

        addBtn.setOnClickListener(view -> {
            // Open the 'activity_upload' when the FloatingActionButton is clicked
            Intent intent = new Intent(home_page.this, UploadActivity.class);
            startActivity(intent);
        });
////////////////////ADD////////////////

        auth = FirebaseAuth.getInstance();
        button = findViewById(R.id.loginout);
        // Inside your home_page onCreate method
        textView = findViewById(R.id.userName);
        user = auth.getCurrentUser();

        String userEmail = getIntent().getStringExtra("user_email");
        String userFullName = getIntent().getStringExtra("user_fullname");

        if (user != null) {
            textView.setText(userFullName != null ? userFullName : user.getEmail());
        } else if (userEmail != null && !userEmail.isEmpty()) {
            textView.setText(userFullName != null ? userFullName : userEmail);
        } else {
            textView.setText("ghost");
        }


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getApplicationContext(), login.class);
                startActivity(intent);
                finish();
            }
        });

        ImageView loginUserImage = findViewById(R.id.loginUser);

        loginUserImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open the profil activity when the loginUserImage is clicked
                startActivity(new Intent(home_page.this, profil.class));
            }
        });

        findViewById(R.id.homepa).setOnClickListener(v -> openNewActivity(home_page.class));
        findViewById(R.id.favorite).setOnClickListener(v -> openNewActivity(Favorite.class));
        findViewById(R.id.seeall).setOnClickListener(v -> openNewActivity(Allevents.class));
        findViewById(R.id.seall).setOnClickListener(v -> openNewActivity(Allevents.class));
        findViewById(R.id.contactus).setOnClickListener(v -> openNewActivity(Contactus.class));
        findViewById(R.id.aboutus).setOnClickListener(v -> openNewActivity(Aboutus.class));

        searchView = findViewById(R.id.searchView);
        searchView.clearFocus();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterList(newText);
                return true;
            }
        });





        initRecyclerView();
    }
    void initRecyclerView() {
        items = new ArrayList<>();
        items.add(new PopularDomain("mawazin", "casa morocco", "a","Immergez-vous dans la richesse culturelle du Festival Mawazine à Casablanca, au Maroc. Découvrez une expérience musicale inoubliable, entourée de l'architecture emblématique de Casa Morocco. Laissez-vous transporter dans un paradis tropical de luxe, où chaque moment devient une aventure.", 2000, true,false, 4.8, "pic1", true, 1000,"https://gateway.naps.ma:8443/GW_PAIEMENT/faces/vues/paiement/gatenaps.xhtml?data1=J0Ie1NSHmzOUHoot2+g7Qmj1/OZtcnIrXgfkzNtK+fF4+TJK3OTUVk2S97IzTA9qLPcuH/0swUOguu+9DNR2bVcv9Hm13388hMraARDri+IneW8glLVSZ1Xb8NwDqmUnzmv/RAxkyQHplPDq5YO1almtqUFfJi4J1M1uwTCXNxy+Q1qEPsawo3PuLW/wQQ/Gz6DFGC/btxC52ld8xNO3dfQmoVDyzA0U4ZHafUpeDx1u3QmgddjdYe/7ACLneyk1Aw1byuWXMIQWGIhy+0lKcuDJPeQMihLTph6xmfPn6LWAUWqNRMqBs5IlZ/9QNB9nkT3YzqL6U7QAjtBRUhpDcg==&data2=akcfFDm0rNEGz4BOLru2eIc32+5rZTLeeC8O61Zl1p08e9nEFrXgdxM2bUSxkXB9g3DMz+5wGNNubNse7nWT/oL/ZD92Yd7r9QpitX6t+VTjOQX6MOsgggLhnDQIllgvLsWDlxc6/s+5qghrb/8w6oHezC35am9m/C5yLwrSEqQCZ6ZeBTgoeSozdgc9epAsdqzDDxIkRhI/ef4AUIHejy7SeKE08WRw/O7viPmI6/J75c6iNC9y/PctNb5sbVbNz48G/Xi6IhKYsRPIu+BvegDN57U4Kr1iM5DrzRZXmzSrUkeS1yleOgmctMgHwlPNrC3fTMF/XfLKbaaqGSsgaQ==&data3=Z8rrCz4ryg/K6cuPhuFhGtzzXOB5LAAqCiwSM2yyW+JEFjiRXi4yaYJ+l8jmqeeMu8w0R/xSujjcpB3rCkS/WsvqZ97c7eMYOrSdUbeEDBGQEANf4LuUBi62L7YK4YOrU/dg+fPk4Dn9HwUUaoZ6WRwpk2q/yhwog8HBxF5IrSFhNXICS8uKrj6/orSK6a3Ig/yasVdpTVzn8AvppW5KoPDvhBpd6tu7uKvlGXFA0vRelk43LQrOxSy83Ky4tAjTy0KEeO0UWlFXz7PaGEH6TZYkwjsZSLDx5Gs8LWiKj0LF32Ye2Vt+bQawlIlePsclgkckTP+m3U79npszVRD0mQ==&data4=NmaNc2RSkxngzI2WCwG8+RjDie2TcXBj7QF9yE3HkAOyWuB1WE4TuWCiJiARoIR6nHT4VWbkMMj1xlD/V0NaW7vzDREHxu8T+tjqlz2F9TvG3urFLyIVUb3s/pjpRql+rREwpdontPdN1iWNbruNZ81Bh04RzkexW+DJI1TXOfk3fzrZbb1WH8ShXMu71xyu+ek5SgRH94YyGh6XQyl0MrtG17UA9PlZzLU7PEmpvFIgcCs/R27UsNKNk/2Mp6KX69wnFvjnEJ1je7MCZDiwBXFpCc+zCJigblaampI4jVok2S9+S+BMOKtXNaiEXG5MwVS0JrGrRbbCs2ArYxXRPw==&cmr=2230513&gal=0180"));
        items.add(new PopularDomain("rachid show", "rabat morocco", "e","Plongez dans un spectacle enchanteur à Rabat, au Maroc, et échappez-vous vers un chalet confortable niché dans les montagnes sereines. Vivez l'harmonie entre divertissement et tranquillité dans cet écrin de nature.", 100, false,false, 5, "pic6",false, 250,"https://gateway.naps.ma:8443/GW_PAIEMENT/faces/vues/paiement/gatenaps.xhtml?data1=J0Ie1NSHmzOUHoot2+g7Qmj1/OZtcnIrXgfkzNtK+fF4+TJK3OTUVk2S97IzTA9qLPcuH/0swUOguu+9DNR2bVcv9Hm13388hMraARDri+IneW8glLVSZ1Xb8NwDqmUnzmv/RAxkyQHplPDq5YO1almtqUFfJi4J1M1uwTCXNxy+Q1qEPsawo3PuLW/wQQ/Gz6DFGC/btxC52ld8xNO3dfQmoVDyzA0U4ZHafUpeDx1u3QmgddjdYe/7ACLneyk1Aw1byuWXMIQWGIhy+0lKcuDJPeQMihLTph6xmfPn6LWAUWqNRMqBs5IlZ/9QNB9nkT3YzqL6U7QAjtBRUhpDcg==&data2=akcfFDm0rNEGz4BOLru2eIc32+5rZTLeeC8O61Zl1p08e9nEFrXgdxM2bUSxkXB9g3DMz+5wGNNubNse7nWT/oL/ZD92Yd7r9QpitX6t+VTjOQX6MOsgggLhnDQIllgvLsWDlxc6/s+5qghrb/8w6oHezC35am9m/C5yLwrSEqQCZ6ZeBTgoeSozdgc9epAsdqzDDxIkRhI/ef4AUIHejy7SeKE08WRw/O7viPmI6/J75c6iNC9y/PctNb5sbVbNz48G/Xi6IhKYsRPIu+BvegDN57U4Kr1iM5DrzRZXmzSrUkeS1yleOgmctMgHwlPNrC3fTMF/XfLKbaaqGSsgaQ==&data3=Z8rrCz4ryg/K6cuPhuFhGtzzXOB5LAAqCiwSM2yyW+JEFjiRXi4yaYJ+l8jmqeeMu8w0R/xSujjcpB3rCkS/WsvqZ97c7eMYOrSdUbeEDBGQEANf4LuUBi62L7YK4YOrU/dg+fPk4Dn9HwUUaoZ6WRwpk2q/yhwog8HBxF5IrSFhNXICS8uKrj6/orSK6a3Ig/yasVdpTVzn8AvppW5KoPDvhBpd6tu7uKvlGXFA0vRelk43LQrOxSy83Ky4tAjTy0KEeO0UWlFXz7PaGEH6TZYkwjsZSLDx5Gs8LWiKj0LF32Ye2Vt+bQawlIlePsclgkckTP+m3U79npszVRD0mQ==&data4=NmaNc2RSkxngzI2WCwG8+RjDie2TcXBj7QF9yE3HkAOyWuB1WE4TuWCiJiARoIR6nHT4VWbkMMj1xlD/V0NaW7vzDREHxu8T+tjqlz2F9TvG3urFLyIVUb3s/pjpRql+rREwpdontPdN1iWNbruNZ81Bh04RzkexW+DJI1TXOfk3fzrZbb1WH8ShXMu71xyu+ek5SgRH94YyGh6XQyl0MrtG17UA9PlZzLU7PEmpvFIgcCs/R27UsNKNk/2Mp6KX69wnFvjnEJ1je7MCZDiwBXFpCc+zCJigblaampI4jVok2S9+S+BMOKtXNaiEXG5MwVS0JrGrRbbCs2ArYxXRPw==&cmr=2230513&gal=0180"));
        items.add(new PopularDomain("chomicha", "marrakech morocco", "s","Laissez-vous séduire par les vues à couper le souffle dans ce complexe en bord de mer à Marrakech, au Maroc. Profitez d'un mélange de luxe et de paysages spectaculaires, créant une expérience inégalée où le raffinement rencontre la nature majestueuse.", 130, true,true, 4.3, "pic4", true, 350,"https://gateway.naps.ma:8443/GW_PAIEMENT/faces/vues/paiement/gatenaps.xhtml?data1=J0Ie1NSHmzOUHoot2+g7Qmj1/OZtcnIrXgfkzNtK+fF4+TJK3OTUVk2S97IzTA9qLPcuH/0swUOguu+9DNR2bVcv9Hm13388hMraARDri+IneW8glLVSZ1Xb8NwDqmUnzmv/RAxkyQHplPDq5YO1almtqUFfJi4J1M1uwTCXNxy+Q1qEPsawo3PuLW/wQQ/Gz6DFGC/btxC52ld8xNO3dfQmoVDyzA0U4ZHafUpeDx1u3QmgddjdYe/7ACLneyk1Aw1byuWXMIQWGIhy+0lKcuDJPeQMihLTph6xmfPn6LWAUWqNRMqBs5IlZ/9QNB9nkT3YzqL6U7QAjtBRUhpDcg==&data2=akcfFDm0rNEGz4BOLru2eIc32+5rZTLeeC8O61Zl1p08e9nEFrXgdxM2bUSxkXB9g3DMz+5wGNNubNse7nWT/oL/ZD92Yd7r9QpitX6t+VTjOQX6MOsgggLhnDQIllgvLsWDlxc6/s+5qghrb/8w6oHezC35am9m/C5yLwrSEqQCZ6ZeBTgoeSozdgc9epAsdqzDDxIkRhI/ef4AUIHejy7SeKE08WRw/O7viPmI6/J75c6iNC9y/PctNb5sbVbNz48G/Xi6IhKYsRPIu+BvegDN57U4Kr1iM5DrzRZXmzSrUkeS1yleOgmctMgHwlPNrC3fTMF/XfLKbaaqGSsgaQ==&data3=Z8rrCz4ryg/K6cuPhuFhGtzzXOB5LAAqCiwSM2yyW+JEFjiRXi4yaYJ+l8jmqeeMu8w0R/xSujjcpB3rCkS/WsvqZ97c7eMYOrSdUbeEDBGQEANf4LuUBi62L7YK4YOrU/dg+fPk4Dn9HwUUaoZ6WRwpk2q/yhwog8HBxF5IrSFhNXICS8uKrj6/orSK6a3Ig/yasVdpTVzn8AvppW5KoPDvhBpd6tu7uKvlGXFA0vRelk43LQrOxSy83Ky4tAjTy0KEeO0UWlFXz7PaGEH6TZYkwjsZSLDx5Gs8LWiKj0LF32Ye2Vt+bQawlIlePsclgkckTP+m3U79npszVRD0mQ==&data4=NmaNc2RSkxngzI2WCwG8+RjDie2TcXBj7QF9yE3HkAOyWuB1WE4TuWCiJiARoIR6nHT4VWbkMMj1xlD/V0NaW7vzDREHxu8T+tjqlz2F9TvG3urFLyIVUb3s/pjpRql+rREwpdontPdN1iWNbruNZ81Bh04RzkexW+DJI1TXOfk3fzrZbb1WH8ShXMu71xyu+ek5SgRH94YyGh6XQyl0MrtG17UA9PlZzLU7PEmpvFIgcCs/R27UsNKNk/2Mp6KX69wnFvjnEJ1je7MCZDiwBXFpCc+zCJigblaampI4jVok2S9+S+BMOKtXNaiEXG5MwVS0JrGrRbbCs2ArYxXRPw==&cmr=2230513&gal=0180"));
        items.add(new PopularDomain("ras sana", "fes morocco","t", "Célébrez le Nouvel An avec style et élégance dans ce paradis tropical de luxe à Fès, au Maroc. Plongez-vous dans une oasis de confort et de beauté, où chaque détail est soigneusement pensé pour créer une expérience inoubliable.", 2000, true,false, 4.8, "pic5", true, 100,"https://gateway.naps.ma:8443/GW_PAIEMENT/faces/vues/paiement/gatenaps.xhtml?data1=J0Ie1NSHmzOUHoot2+g7Qmj1/OZtcnIrXgfkzNtK+fF4+TJK3OTUVk2S97IzTA9qLPcuH/0swUOguu+9DNR2bVcv9Hm13388hMraARDri+IneW8glLVSZ1Xb8NwDqmUnzmv/RAxkyQHplPDq5YO1almtqUFfJi4J1M1uwTCXNxy+Q1qEPsawo3PuLW/wQQ/Gz6DFGC/btxC52ld8xNO3dfQmoVDyzA0U4ZHafUpeDx1u3QmgddjdYe/7ACLneyk1Aw1byuWXMIQWGIhy+0lKcuDJPeQMihLTph6xmfPn6LWAUWqNRMqBs5IlZ/9QNB9nkT3YzqL6U7QAjtBRUhpDcg==&data2=akcfFDm0rNEGz4BOLru2eIc32+5rZTLeeC8O61Zl1p08e9nEFrXgdxM2bUSxkXB9g3DMz+5wGNNubNse7nWT/oL/ZD92Yd7r9QpitX6t+VTjOQX6MOsgggLhnDQIllgvLsWDlxc6/s+5qghrb/8w6oHezC35am9m/C5yLwrSEqQCZ6ZeBTgoeSozdgc9epAsdqzDDxIkRhI/ef4AUIHejy7SeKE08WRw/O7viPmI6/J75c6iNC9y/PctNb5sbVbNz48G/Xi6IhKYsRPIu+BvegDN57U4Kr1iM5DrzRZXmzSrUkeS1yleOgmctMgHwlPNrC3fTMF/XfLKbaaqGSsgaQ==&data3=Z8rrCz4ryg/K6cuPhuFhGtzzXOB5LAAqCiwSM2yyW+JEFjiRXi4yaYJ+l8jmqeeMu8w0R/xSujjcpB3rCkS/WsvqZ97c7eMYOrSdUbeEDBGQEANf4LuUBi62L7YK4YOrU/dg+fPk4Dn9HwUUaoZ6WRwpk2q/yhwog8HBxF5IrSFhNXICS8uKrj6/orSK6a3Ig/yasVdpTVzn8AvppW5KoPDvhBpd6tu7uKvlGXFA0vRelk43LQrOxSy83Ky4tAjTy0KEeO0UWlFXz7PaGEH6TZYkwjsZSLDx5Gs8LWiKj0LF32Ye2Vt+bQawlIlePsclgkckTP+m3U79npszVRD0mQ==&data4=NmaNc2RSkxngzI2WCwG8+RjDie2TcXBj7QF9yE3HkAOyWuB1WE4TuWCiJiARoIR6nHT4VWbkMMj1xlD/V0NaW7vzDREHxu8T+tjqlz2F9TvG3urFLyIVUb3s/pjpRql+rREwpdontPdN1iWNbruNZ81Bh04RzkexW+DJI1TXOfk3fzrZbb1WH8ShXMu71xyu+ek5SgRH94YyGh6XQyl0MrtG17UA9PlZzLU7PEmpvFIgcCs/R27UsNKNk/2Mp6KX69wnFvjnEJ1je7MCZDiwBXFpCc+zCJigblaampI4jVok2S9+S+BMOKtXNaiEXG5MwVS0JrGrRbbCs2ArYxXRPw==&cmr=2230513&gal=0180"));
        items.add(new PopularDomain("mawahib", "Tangier morocco","e", "Évadez-vous vers un chalet confortable niché dans les montagnes paisibles et découvrez les trésors cachés de Tanger, au Maroc. Laissez-vous enchanter par la magie de cette ville côtière, où l'histoire se mêle à la modernité dans une fusion captivante.", 120, false,true, 5, "pic2",false, 200,"https://gateway.naps.ma:8443/GW_PAIEMENT/faces/vues/paiement/gatenaps.xhtml?data1=J0Ie1NSHmzOUHoot2+g7Qmj1/OZtcnIrXgfkzNtK+fF4+TJK3OTUVk2S97IzTA9qLPcuH/0swUOguu+9DNR2bVcv9Hm13388hMraARDri+IneW8glLVSZ1Xb8NwDqmUnzmv/RAxkyQHplPDq5YO1almtqUFfJi4J1M1uwTCXNxy+Q1qEPsawo3PuLW/wQQ/Gz6DFGC/btxC52ld8xNO3dfQmoVDyzA0U4ZHafUpeDx1u3QmgddjdYe/7ACLneyk1Aw1byuWXMIQWGIhy+0lKcuDJPeQMihLTph6xmfPn6LWAUWqNRMqBs5IlZ/9QNB9nkT3YzqL6U7QAjtBRUhpDcg==&data2=akcfFDm0rNEGz4BOLru2eIc32+5rZTLeeC8O61Zl1p08e9nEFrXgdxM2bUSxkXB9g3DMz+5wGNNubNse7nWT/oL/ZD92Yd7r9QpitX6t+VTjOQX6MOsgggLhnDQIllgvLsWDlxc6/s+5qghrb/8w6oHezC35am9m/C5yLwrSEqQCZ6ZeBTgoeSozdgc9epAsdqzDDxIkRhI/ef4AUIHejy7SeKE08WRw/O7viPmI6/J75c6iNC9y/PctNb5sbVbNz48G/Xi6IhKYsRPIu+BvegDN57U4Kr1iM5DrzRZXmzSrUkeS1yleOgmctMgHwlPNrC3fTMF/XfLKbaaqGSsgaQ==&data3=Z8rrCz4ryg/K6cuPhuFhGtzzXOB5LAAqCiwSM2yyW+JEFjiRXi4yaYJ+l8jmqeeMu8w0R/xSujjcpB3rCkS/WsvqZ97c7eMYOrSdUbeEDBGQEANf4LuUBi62L7YK4YOrU/dg+fPk4Dn9HwUUaoZ6WRwpk2q/yhwog8HBxF5IrSFhNXICS8uKrj6/orSK6a3Ig/yasVdpTVzn8AvppW5KoPDvhBpd6tu7uKvlGXFA0vRelk43LQrOxSy83Ky4tAjTy0KEeO0UWlFXz7PaGEH6TZYkwjsZSLDx5Gs8LWiKj0LF32Ye2Vt+bQawlIlePsclgkckTP+m3U79npszVRD0mQ==&data4=NmaNc2RSkxngzI2WCwG8+RjDie2TcXBj7QF9yE3HkAOyWuB1WE4TuWCiJiARoIR6nHT4VWbkMMj1xlD/V0NaW7vzDREHxu8T+tjqlz2F9TvG3urFLyIVUb3s/pjpRql+rREwpdontPdN1iWNbruNZ81Bh04RzkexW+DJI1TXOfk3fzrZbb1WH8ShXMu71xyu+ek5SgRH94YyGh6XQyl0MrtG17UA9PlZzLU7PEmpvFIgcCs/R27UsNKNk/2Mp6KX69wnFvjnEJ1je7MCZDiwBXFpCc+zCJigblaampI4jVok2S9+S+BMOKtXNaiEXG5MwVS0JrGrRbbCs2ArYxXRPw==&cmr=2230513&gal=0180"));
        items.add(new PopularDomain("match koora", "Algeria", "r","Plongez-vous dans l'excitation d'un match de football en Algérie et profitez des vues à couper le souffle dans un complexe en bord de mer. Découvrez l'énergie palpitante du sport et la sérénité de l'océan, créant une expérience équilibrée entre passion et détente.", 30000, true,false, 4.3, "pic3", true, 300,"https://gateway.naps.ma:8443/GW_PAIEMENT/faces/vues/paiement/gatenaps.xhtml?data1=J0Ie1NSHmzOUHoot2+g7Qmj1/OZtcnIrXgfkzNtK+fF4+TJK3OTUVk2S97IzTA9qLPcuH/0swUOguu+9DNR2bVcv9Hm13388hMraARDri+IneW8glLVSZ1Xb8NwDqmUnzmv/RAxkyQHplPDq5YO1almtqUFfJi4J1M1uwTCXNxy+Q1qEPsawo3PuLW/wQQ/Gz6DFGC/btxC52ld8xNO3dfQmoVDyzA0U4ZHafUpeDx1u3QmgddjdYe/7ACLneyk1Aw1byuWXMIQWGIhy+0lKcuDJPeQMihLTph6xmfPn6LWAUWqNRMqBs5IlZ/9QNB9nkT3YzqL6U7QAjtBRUhpDcg==&data2=akcfFDm0rNEGz4BOLru2eIc32+5rZTLeeC8O61Zl1p08e9nEFrXgdxM2bUSxkXB9g3DMz+5wGNNubNse7nWT/oL/ZD92Yd7r9QpitX6t+VTjOQX6MOsgggLhnDQIllgvLsWDlxc6/s+5qghrb/8w6oHezC35am9m/C5yLwrSEqQCZ6ZeBTgoeSozdgc9epAsdqzDDxIkRhI/ef4AUIHejy7SeKE08WRw/O7viPmI6/J75c6iNC9y/PctNb5sbVbNz48G/Xi6IhKYsRPIu+BvegDN57U4Kr1iM5DrzRZXmzSrUkeS1yleOgmctMgHwlPNrC3fTMF/XfLKbaaqGSsgaQ==&data3=Z8rrCz4ryg/K6cuPhuFhGtzzXOB5LAAqCiwSM2yyW+JEFjiRXi4yaYJ+l8jmqeeMu8w0R/xSujjcpB3rCkS/WsvqZ97c7eMYOrSdUbeEDBGQEANf4LuUBi62L7YK4YOrU/dg+fPk4Dn9HwUUaoZ6WRwpk2q/yhwog8HBxF5IrSFhNXICS8uKrj6/orSK6a3Ig/yasVdpTVzn8AvppW5KoPDvhBpd6tu7uKvlGXFA0vRelk43LQrOxSy83Ky4tAjTy0KEeO0UWlFXz7PaGEH6TZYkwjsZSLDx5Gs8LWiKj0LF32Ye2Vt+bQawlIlePsclgkckTP+m3U79npszVRD0mQ==&data4=NmaNc2RSkxngzI2WCwG8+RjDie2TcXBj7QF9yE3HkAOyWuB1WE4TuWCiJiARoIR6nHT4VWbkMMj1xlD/V0NaW7vzDREHxu8T+tjqlz2F9TvG3urFLyIVUb3s/pjpRql+rREwpdontPdN1iWNbruNZ81Bh04RzkexW+DJI1TXOfk3fzrZbb1WH8ShXMu71xyu+ek5SgRH94YyGh6XQyl0MrtG17UA9PlZzLU7PEmpvFIgcCs/R27UsNKNk/2Mp6KX69wnFvjnEJ1je7MCZDiwBXFpCc+zCJigblaampI4jVok2S9+S+BMOKtXNaiEXG5MwVS0JrGrRbbCs2ArYxXRPw==&cmr=2230513&gal=0180"));

        // Filter items where place is greater than 1500
        ArrayList<PopularDomain> filteredItems = new ArrayList<>();
        for (PopularDomain item : items) {
            if (item.getPlace() > 1500) {
                filteredItems.add(item);
            }
        }

        recyclerViewPopular = findViewById(R.id.view_pop);
        recyclerViewPopular.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        PupolarAdapter adapterPopular = new PupolarAdapter(filteredItems); // Assuming "PupolarAdapter" is your adapter class
        recyclerViewPopular.setAdapter(adapterPopular);

        adapterPopular.setOnItemClickListener(position -> {
            Intent intent = new Intent(home_page.this, DetailActivity.class);
            intent.putExtra("object", filteredItems.get(position));
            intent.putExtra("items", filteredItems);
            startActivity(intent);
        });

        recyclerViewAll = findViewById(R.id.view_all);
        recyclerViewAll.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        adapterAll = new AllAdapter(items); // Assuming "AllAdapter" is your adapter class
        recyclerViewAll.setAdapter(adapterAll);

        ArrayList<CategoryDomain> catsList = new ArrayList<>();
        catsList.add(new CategoryDomain("Fêtes", "f"));
        catsList.add(new CategoryDomain("Arts", "a"));
        catsList.add(new CategoryDomain("Sports", "s"));
        catsList.add(new CategoryDomain("Éducatif", "e"));
        catsList.add(new CategoryDomain("Technologie", "t"));
        catsList.add(new CategoryDomain("Religieux", "r"));

        recyclerViewCategory = findViewById(R.id.view_cat);
        recyclerViewCategory.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        adapterCat = new CategoryAdapter(catsList, position -> openCatActivity(catsList.get(position)));
        recyclerViewCategory.setAdapter(adapterCat);
    }


    private void openCatActivity(CategoryDomain selectedCategory) {
        Intent intent = new Intent(home_page.this, cat_Activity.class);

        // Pass the list of events to cat_Activity
        intent.putExtra("eventsList", items);

        // Pass the selected category's name
        intent.putExtra("categoryName", selectedCategory.getTitle());

        startActivity(intent);
    }

    /////////shearch/////////part//////////
    private void filterList(String text) {
        ArrayList<PopularDomain> filteredList = new ArrayList<>();
        for (PopularDomain i : items) {
            if (i.getTitle().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(i);
            }
        }
        if (filteredList.isEmpty()) {
            Toast.makeText(this, "No data found", Toast.LENGTH_SHORT).show();
        } else {
            if (adapterAll instanceof AllAdapter) {
                ((AllAdapter) adapterAll).setFilteredList(filteredList);
            }
        }
    }
}