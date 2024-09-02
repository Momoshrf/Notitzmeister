package com.example.notesapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.notesapp.DataBase.DatabaseHelper;

public class SignupActivity extends AppCompatActivity {
    private EditText usernameEditText;
    private EditText passwordEditText;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
// Initialize the DatabaseHelper object
        dbHelper = new DatabaseHelper(this);
        usernameEditText = findViewById(R.id.usernameEditText);
        passwordEditText = findViewById(R.id.passwordEditText);

        dbHelper = new DatabaseHelper(this);
    }

    public void signup(View view) {
        String username = usernameEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();

        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("username", username);
        values.put("password", password);

        long newRowId = db.insert("users", null, values);

        if (newRowId != -1) {
            // Handle successful signup, maybe redirect to login activity
            startActivity(new Intent(this, Activity_Login.class));
            Log.d("SignupActivity", "User signed up successfully with username: " + username);
        } else {
            // Handle error
        }
    }

    public void login(View view) {
        // Redirect to another activity, e.g., HomeActivity
        startActivity(new Intent(this, Activity_Login.class));
    }
}
