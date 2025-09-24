package com.example.wellness360;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.CompoundButton;

public class Startsteps extends AppCompatActivity {

    private static final String SHARED_PREFS_KEY = "checkbox_shared_prefs2";
    private static final String CHECKBOX_KEY1 = "checkbox_key1";
    private static final String CHECKBOX_KEY2 = "checkbox_key2";
    private static final String CHECKBOX_KEY3 = "checkbox_key3";
    private static final String CHECKBOX_KEY4 = "checkbox_key4";
    private CheckBox checkBox1, checkBox2, checkBox3, checkBox4;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startsteps);

        sharedPreferences = getSharedPreferences(SHARED_PREFS_KEY, Context.MODE_PRIVATE);
        checkBox1 = findViewById(R.id.checkBox);
        checkBox2 = findViewById(R.id.checkBox1);
        checkBox3 = findViewById(R.id.checkBox2);
        checkBox4 = findViewById(R.id.checkBox3);
        checkBox1.setChecked(sharedPreferences.getBoolean(CHECKBOX_KEY1, false));
        checkBox2.setChecked(sharedPreferences.getBoolean(CHECKBOX_KEY2, false));
        checkBox3.setChecked(sharedPreferences.getBoolean(CHECKBOX_KEY3, false));
        checkBox4.setChecked(sharedPreferences.getBoolean(CHECKBOX_KEY4, false));

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