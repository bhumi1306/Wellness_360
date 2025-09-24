package com.example.wellness360;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Stepsplan extends AppCompatActivity {

    private Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stepsplan);
        btn = (Button) findViewById(R.id.start2);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openplan();
            }
        });
    }
    public void openplan(){
        Intent intent = new Intent(this, Startsteps.class);
        startActivity(intent);
        finish();
    }
}