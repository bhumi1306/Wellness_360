package com.example.wellness360;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;
import android.content.Context;
import android.content.SharedPreferences;
import androidx.appcompat.app.AlertDialog;
import android.os.Bundle;

public class startmed extends AppCompatActivity {

    private static final String SHARED_PREFS_KEY = "checkbox_shared_prefs";
    private static final String CHECKBOX_KEY1 = "checkbox_key1";
    private static final String CHECKBOX_KEY2 = "checkbox_key2";
    private static final String CHECKBOX_KEY3 = "checkbox_key3";
    private static final String CHECKBOX_KEY4 = "checkbox_key4";
    private CheckBox checkBox1, checkBox2, checkBox3, checkBox4;
    private SharedPreferences sharedPreferences;
    Button musicButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startmed);
        sharedPreferences = getSharedPreferences(SHARED_PREFS_KEY, Context.MODE_PRIVATE);
        checkBox1 = findViewById(R.id.checkBox);
        checkBox2 = findViewById(R.id.checkBox1);
        checkBox3 = findViewById(R.id.checkBox2);
        checkBox4 = findViewById(R.id.checkBox3);
        checkBox1.setChecked(sharedPreferences.getBoolean(CHECKBOX_KEY1, false));
        checkBox2.setChecked(sharedPreferences.getBoolean(CHECKBOX_KEY2, false));
        checkBox3.setChecked(sharedPreferences.getBoolean(CHECKBOX_KEY3, false));
        checkBox4.setChecked(sharedPreferences.getBoolean(CHECKBOX_KEY4, false));
        musicButton = findViewById(R.id.imageButton2);
        musicButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String playlistUri = "https://open.spotify.com/playlist/37i9dQZF1DWZqd5JICZI0u?si=iagAI_a6StCU7NawJudYxg&pi=a-AcQTlgpTT2Sm";

                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(playlistUri));
                intent.setPackage("com.spotify.music");
                PackageManager packageManager = getPackageManager();
                if (intent.resolveActivity(packageManager) != null) {
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "Spotify app not installed", Toast.LENGTH_SHORT).show();
                }
            }
        });

        checkBox1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                sharedPreferences.edit().putBoolean(CHECKBOX_KEY1, isChecked).apply();
                checkAllCheckboxes();
            }
        });
        checkBox2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                sharedPreferences.edit().putBoolean(CHECKBOX_KEY2, isChecked).apply();
                checkAllCheckboxes();
            }
        });
        checkBox3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                sharedPreferences.edit().putBoolean(CHECKBOX_KEY3, isChecked).apply();
                checkAllCheckboxes();
            }
        });
        checkBox4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                sharedPreferences.edit().putBoolean(CHECKBOX_KEY4, isChecked).apply();
                checkAllCheckboxes();
            }
        });

    }
    private void checkAllCheckboxes() {
        if (checkBox1.isChecked() && checkBox2.isChecked() && checkBox3.isChecked() && checkBox4.isChecked()) {
            showAlertDialog();
        }
    }

    private void showAlertDialog() {
        new AlertDialog.Builder(this)
                .setTitle("PLAN COMPLETED!")
                .setMessage("You have successfully completed the plan.")
                .setPositiveButton(android.R.string.ok, null)
                .show();
}

}