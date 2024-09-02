package com.example.notesapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.notesapp.simplenotes.DeleteNotesActivity;
import com.example.notesapp.simplenotes.NoteActivity;
import com.example.notesapp.simplenotes.NotesListActivity;
import com.example.notesapp.simplenotes.SettingsActivity;

public class MainScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);

        // Initialize Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }

        // Set up toolbar actions
        setupToolbarActions();
    }

    private void setupToolbarActions() {
        // Handle settings button click
        ImageButton settingsButton = findViewById(R.id.settings_button);
        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSettings();
            }
        });

        // Handle logout button click
        ImageButton logoutButton = findViewById(R.id.logout_button);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });
    }

    // Methods to handle button actions
    public void showNotes(View view) {
        startActivity(new Intent(MainScreenActivity.this, NotesListActivity.class));
    }

    public void createNotes(View view) {
        startActivity(new Intent(MainScreenActivity.this, NoteActivity.class));
    }

    public void deleteNotes(View view) {
        // Start DeleteNotesActivity
        startActivity(new Intent(MainScreenActivity.this, DeleteNotesActivity.class));
    }


    public void openSettings() {
        startActivity(new Intent(MainScreenActivity.this, SettingsActivity.class));
    }

    public void logout() {
        // Clear session data
        logoutUser();

        // Redirect to login activity
        Intent intent = new Intent(MainScreenActivity.this, Activity_Login.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();  // Finish current activity to prevent going back to it
    }

    // Method to clear user session data
    private void logoutUser() {
        SharedPreferences.Editor editor = getSharedPreferences("user_prefs", Context.MODE_PRIVATE).edit();
        editor.remove("registered");
        editor.apply();
    }
}
