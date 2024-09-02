package com.example.notesapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.notesapp.DataBase.DatabaseHelper;

public class Activity_Login extends AppCompatActivity {

    private EditText usernameEditText;
    private EditText passwordEditText;
    private SQLiteDatabase db;
    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);
// Initialize the DatabaseHelper object
        databaseHelper = new DatabaseHelper(this);
        usernameEditText = findViewById(R.id.usernameEditText);
        passwordEditText = findViewById(R.id.passwordEditText);

        // Open the database (or create it if it doesn't exist)
        db = openOrCreateDatabase("users.db", Context.MODE_PRIVATE, null);

        // Create a table if it doesn't exist
        db.execSQL("CREATE TABLE IF NOT EXISTS users (username VARCHAR, password VARCHAR)");

        // Add a test user (you can remove this in production)
        db.execSQL("INSERT INTO users VALUES ('testuser', 'password123')");

        // Check if the user is already registered
        if (isUserRegistered()) {
            // User is already registered, open the desired activity
            startActivity(new Intent(Activity_Login.this, MainScreenActivity.class));
            finish(); // Optional: finish the current activity to prevent going back to it on back press
        }
    }



    public void login2(View view) {
        String username = usernameEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();

        SQLiteDatabase db = databaseHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM users WHERE username=? AND password=?", new String[]{username, password});
        if (cursor.moveToFirst()) {
            // Successful login
            Toast.makeText(this, "Login successful", Toast.LENGTH_SHORT).show();
            // Redirect to another activity, e.g., HomeActivity
            startActivity(new Intent(this, MainScreenActivity.class));
            // Set user as registered in SharedPreferences
            setUserRegistered();
            Log.d("LoginActivity", "User logged in successfully with username: " + username);
        } else {
            // Invalid credentials
            Toast.makeText(this, "Invalid username or password", Toast.LENGTH_SHORT).show();
            Log.d("LoginActivity", "Invalid login attempt with username: " + username);
        }
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Close the database connection when the activity is destroyed
        if (db != null) {
            db.close();
        }
    }

    public void signup(View view) {
        startActivity(new Intent(this, SignupActivity.class));
    }

    // Example method to check if the user is registered using SharedPreferences
    private boolean isUserRegistered() {
        // Retrieve the SharedPreferences instance
        SharedPreferences sharedPreferences = getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
        // Check if the "registered" key exists and its value is true
        return sharedPreferences.getBoolean("registered", false);
    }

    // Example method to set the user as registered in SharedPreferences
    private void setUserRegistered() {
        // Get the SharedPreferences editor
        SharedPreferences.Editor editor = getSharedPreferences("user_prefs", Context.MODE_PRIVATE).edit();
        // Set the "registered" key to true
        editor.putBoolean("registered", true);
        // Apply the changes
        editor.apply();
    }
}
