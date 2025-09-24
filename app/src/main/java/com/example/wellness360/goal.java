package com.example.wellness360;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class goal extends AppCompatActivity {
private Button button;
    private static final String BUTTON_PREFS = "ButtonPrefs";
    private static final String BUTTON_KEY_PREFIX = "ButtonKey_";
    private List<Button> buttons = new ArrayList<>();

boolean clicked= false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goal);

        GlobalData globalData= GlobalData.getInstance();
        button = (Button) findViewById(R.id.button6);
        Button button2 = (Button) findViewById(R.id.button1);
        Button button3 = (Button) findViewById(R.id.button2);
        Button button4 = (Button) findViewById(R.id.button3);
        Button button5 = (Button) findViewById(R.id.button4);
        Button button6 = (Button) findViewById(R.id.button5);
        buttons.add(button2);
        buttons.add(button3);
        buttons.add(button4);
        buttons.add(button5);
        buttons.add(button6);
//        button2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                toggleButtonSelection(button);
//                button2.setSelected(!button2.isSelected());
//            }
//        });
//        button3.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                toggleButtonSelection(button);
//                button3.setSelected(!button3.isSelected());
//            }
//        });
//        button4.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                toggleButtonSelection(button);
//                button4.setSelected(!button4.isSelected());
//            }
//        });
//        button5.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                toggleButtonSelection(button);
//                button5.setSelected(!button5.isSelected());
//            }
//        });
//        button6.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                toggleButtonSelection(button);
//                button6.setSelected(!button6.isSelected());
//            }
//        });
        for(Button button : buttons){
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    toggleButtonSelection(button);
                }
            });
        }
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        SharedPreferences preferences = getSharedPreferences(BUTTON_PREFS, MODE_PRIVATE);
        for (Button button : buttons) {
            boolean isSelected = preferences.getBoolean(BUTTON_KEY_PREFIX + button.getId(), false);
            button.setSelected(isSelected);
        }

}
    private void toggleButtonSelection(Button button) {
        boolean isSelected = !button.isSelected();
        button.setSelected(isSelected);
        saveButtonState(button.getId(), isSelected);
    }

    private void saveButtonState(int buttonId, boolean isSelected) {
        SharedPreferences preferences = getSharedPreferences(BUTTON_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(BUTTON_KEY_PREFIX + buttonId, isSelected);
        editor.apply();
    }

    public void onSaveClick(View view) {
        // Handle the save button click if needed
}

//    public void openActivity2() {
//        if(clicked) {
//            Intent intent = new Intent(this, detail.class);
//            startActivity(intent);
//        }
//        else {
//            Toast.makeText(getApplicationContext(), "Please select atleast one goal!", Toast.LENGTH_SHORT).show();
//        }
//    }

}