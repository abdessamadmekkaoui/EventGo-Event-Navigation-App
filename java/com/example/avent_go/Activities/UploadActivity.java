package com.example.avent_go.Activities;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.avent_go.Domains.PopularDomain;
import com.example.avent_go.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class UploadActivity extends AppCompatActivity {


    ImageView uploadImage;
    Button SaveButton,CancelButton;
    CheckBox uploadEat,uploadWifi,uploadFavorite;
    EditText uploadTitle, uploadDescription, uploadCategory,uploadLocation,uploadUrl,uploadPlace,uploadScore,uploadPrice;
    String imageURL;
    Uri uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);


        // Initialize views
        uploadImage = findViewById(R.id.etPic);
        uploadDescription = findViewById(R.id.etDescription);
        uploadTitle = findViewById(R.id.etTitle);
        uploadLocation = findViewById(R.id.etLocation);
        uploadCategory = findViewById(R.id.etCategory);
        uploadUrl = findViewById(R.id.etUrl);
        uploadPlace = findViewById(R.id.etPlace);
        SaveButton = findViewById(R.id.etSave);
        CancelButton = findViewById(R.id.etCancel);
        uploadScore = findViewById(R.id.etScore);
        uploadPrice = findViewById(R.id.etPrice);
        uploadEat = findViewById(R.id.etEat);
        uploadWifi = findViewById(R.id.etWifi);
        uploadFavorite = findViewById(R.id.etFavorite);

        ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            Intent data = result.getData();
                            uri = data.getData();
                            uploadImage.setImageURI(uri);
                        } else {
                            Toast.makeText(UploadActivity.this, "No image selected", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );
        uploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent photoPicker = new Intent(Intent.ACTION_PICK);
                photoPicker.setType("image/");
                activityResultLauncher.launch(photoPicker);
            }
        });

        SaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               saveData();
            }
        });

    }
    public void saveData() {
        StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("Android Images").child(uri.getLastPathSegment());

        AlertDialog.Builder builder = new AlertDialog.Builder(UploadActivity.this);
        builder.setCancelable(false);
        builder.setView(R.layout.progress_layout);
        AlertDialog dialog = builder.create();
        dialog.show();

        storageReference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();

                while (!uriTask.isComplete());
                Uri urlImage = uriTask.getResult();
                imageURL = urlImage.toString();
                uploadData();
                dialog.dismiss();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                dialog.dismiss();
            }
        });
    }

    public void uploadData() {
        String title = uploadTitle.getText().toString();
        String description = uploadDescription.getText().toString();
        String location = uploadLocation.getText().toString();
        String category = uploadCategory.getText().toString();
        String url = uploadUrl.getText().toString();
        int place = Integer.parseInt(uploadPlace.getText().toString());
        int price = Integer.parseInt(uploadPrice.getText().toString());
        boolean eat = Boolean.parseBoolean(uploadEat.getText().toString());
        boolean favorite = Boolean.parseBoolean(uploadFavorite.getText().toString());
        boolean wifi = Boolean.parseBoolean(uploadWifi.getText().toString());
        double score = Double.parseDouble(uploadScore.getText().toString());

        PopularDomain dataClass = new PopularDomain(title,category,location, description,place,eat,favorite,score,imageURL,wifi,price,url);




        FirebaseDatabase.getInstance().getReference("Android Tutorials").child(title)
                .setValue(dataClass).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(UploadActivity.this, "Saved", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                       Toast.makeText(UploadActivity.this, e.getMessage().toString(),Toast.LENGTH_SHORT).show();
                    }
                });
    }
}