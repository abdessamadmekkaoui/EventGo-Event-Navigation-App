package com.example.avent_go.Activities;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.avent_go.R;

public class AddEventDialog extends DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        // Use the layout file you created for the dialog
        // Example: R.layout.dialog_add_event
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_add_event, null);

        // Set up your UI elements and logic here

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(view)
                .setTitle("Add New Event")
                .setPositiveButton("Add", (dialog, which) -> {
                    // Handle adding the new event
                })
                .setNegativeButton("Cancel", (dialog, which) -> {
                    // Cancel button clicked
                });

        return builder.create();
    }
}
