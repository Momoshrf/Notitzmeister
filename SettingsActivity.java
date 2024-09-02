package com.example.notesapp.simplenotes;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.example.notesapp.R;

public class SettingsActivity extends AppCompatActivity {

    private SharedPreferences preferences;
    private int fontSize;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        preferences = PreferenceManager.getDefaultSharedPreferences(SettingsActivity.this);
        getSettings(preferences);
        applySettings();
    }

    private void getSettings(SharedPreferences preferences) {
        fontSize = preferences.getInt(HelperUtils.PREFERENCE_FONT_SIZE, 16); // Default font size is 16dp
    }

    private void applySettings() {
        TextView textView = findViewById(R.id.tv_example);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, fontSize);
    }

    public void changeFontSize(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Wählen Sie Schriftgröße");

        String[] fontSizes = {"Klein", "Mittel", "Groß"};
        builder.setItems(fontSizes, (dialog, which) -> {
            switch (which) {
                case 0:
                    fontSize = 12; // Small
                    break;
                case 1:
                    fontSize = 16; // Medium
                    break;
                case 2:
                    fontSize = 20; // Large
                    break;
            }
            applySettings(); // Update UI
        });

        builder.show();
    }
}
