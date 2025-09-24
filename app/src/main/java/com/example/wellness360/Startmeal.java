package com.example.wellness360;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.CompoundButton;

public class Startmeal extends AppCompatActivity {

    private static final String SHARED_PREFS_KEY = "checkbox_shared_prefs3";
    private static final String CHECKBOX_KEY1 = "checkbox_key1";
    private static final String CHECKBOX_KEY2 = "checkbox_key2";
    private CheckBox checkBox1, checkBox2;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startmeal);
        sharedPreferences = getSharedPreferences(SHARED_PREFS_KEY, Context.MODE_PRIVATE);
        checkBox1 = findViewById(R.id.checkBox);
        checkBox2 = findViewById(R.id.checkBox2);
        checkBox1.setChecked(sharedPreferences.getBoolean(CHECKBOX_KEY1, false));
        checkBox2.setChecked(sharedPreferences.getBoolean(CHECKBOX_KEY2, false));
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

    }
    private void checkAllCheckboxes() {
        if (checkBox1.isChecked() && checkBox2.isChecked()) {
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