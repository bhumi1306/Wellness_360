package com.example.wellness360;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.util.Patterns;
import android.view.View;
import android.os.Bundle;
import android.widget.Button;
import android.text.InputFilter ;
import android.widget.EditText ;

public class detail extends AppCompatActivity {
    private Button button;
    EditText name, age,gender,height,weight,country;
    boolean isAllFieldsChecked = false;
    Button done;
    private DBHandler dbHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        name = findViewById(R.id.editTextname);
        age = findViewById(R.id.editTextNumber);
        gender = findViewById(R.id.textviewg);
        height = findViewById(R.id.editTextText2);
        weight = findViewById(R.id.editTextText3);
        country = findViewById(R.id.editTextText4);
        done = findViewById(R.id.button8);
        age.setFilters(new InputFilter[]{new MinMax("1", "99")});
        dbHandler= new DBHandler(detail.this);
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isAllFieldsChecked = CheckAllFields();
                String name1 = name.getText().toString();
                String age1 = age.getText().toString();
                String gender1 = gender.getText().toString();
                String height1 = height.getText().toString();
                String weight1 = weight.getText().toString();
                String country1 = country.getText().toString();
                if (isAllFieldsChecked) {
                    dbHandler.addInfo1(name1,age1,gender1,height1,weight1,country1);
                    name.setText("");
                    gender.setText("");
                    age.setText("");
                    height.setText("");
                    weight.setText("");
                    country.setText("");
                    openActivity2();
                }
            }
        });
    }

    public void openActivity2(){
        finish();
        Intent intent = new Intent(this, MainActivity3.class);
        startActivity(intent);
    }
    private boolean CheckAllFields() {
        String name1 = name.getText().toString();
        String age1 = age.getText().toString();
        String gender1 = gender.getText().toString();
        String height1 = height.getText().toString();
        String weight1 = weight.getText().toString();
        String country1 = country.getText().toString();
        if (name1.length() == 0) {
            name.setError("Name is required");
            return false;
        }
        if (age1.length() == 0) {
            age.setError("Age is required");
            return false;
        }
        if (gender1.length() == 0) {
            gender.setError("Gender is required");
            return false;
        }
        if (height1.length() == 0) {
            height.setError("Height is required");
            return false;
        }
        if (weight1.length() == 0) {
            weight.setError("Weight is required");
            return false;
        }
        if (country1.length() == 0) {
            country.setError("Country is required");
            return false;
        }
        return true;
    }
}