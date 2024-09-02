package com.example.notesapp.simplenotes;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notesapp.R;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class DeleteNotesActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private NotesListAdapter adapter;
    private Button deleteButton;
    private CheckBox selectAllCheckbox;
    private List<File> filesList = new ArrayList<>(); // Initialize as an empty list

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_notes);

        recyclerView = findViewById(R.id.recycler_view);
        deleteButton = findViewById(R.id.delete_button);
        selectAllCheckbox = findViewById(R.id.select_all_checkbox);

        setupRecyclerView();
        loadFiles(); // Load files from storage or database

        deleteButton.setOnClickListener(v -> showDeleteConfirmationDialog());

        selectAllCheckbox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                adapter.selectAllFiles();
            } else {
                adapter.deselectAllFiles();
            }
        });
    }

    private void setupRecyclerView() {
        int colorText = getResources().getColor(R.color.colorText, getTheme());
        int colorBackground = getResources().getColor(R.color.colorBackground, getTheme());
        adapter = new NotesListAdapter(colorText, colorBackground);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    private void loadFiles() {
        // Implement this method to retrieve and load your files
        filesList = HelperUtils.getFiles(this); // Assuming you have a method to get files
        adapter.updateList(filesList, true); // Update the adapter with the list
    }

    private void showDeleteConfirmationDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Delete Notes")
                .setMessage("Are you sure you want to delete the selected notes?")
                .setPositiveButton("Yes", (dialog, which) -> deleteSelectedNotes())
                .setNegativeButton("No", null)
                .show();
    }

    private void deleteSelectedNotes() {
        List<File> selectedFiles = adapter.getSelectedFiles();
        if (selectedFiles.isEmpty()) {
            Toast.makeText(this, "No notes selected for deletion", Toast.LENGTH_SHORT).show();
            return;
        }

        for (File file : selectedFiles) {
            if (file.exists() && file.delete()) {
                Toast.makeText(this, "Deleted: " + file.getName(), Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Failed to delete: " + file.getName(), Toast.LENGTH_SHORT).show();
            }
        }

        loadFiles(); // Reload files after deletion
    }

    public static Intent getStartIntent(Context context) {
        return new Intent(context, DeleteNotesActivity.class);
    }
}
