package com.example.wellness360;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class SearchResult extends AppCompatActivity {
    private TextView resultTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);

        resultTextView = findViewById(R.id.resultTextView);
        String searchQuery = getIntent().getStringExtra("searchQuery");

        if (searchQuery != null) {
            fetchFoodDetails(searchQuery);
        }
    }
    private void fetchFoodDetails(String query) {
        FoodDetailsRequest.fetchFoodDetails(this, query, new FoodDetailsRequest.FoodDetailsListener() {
            @Override
            public void onFoodDetailsReceived(FoodDetailsModel foodDetails) {
                String searchQuery = getIntent().getStringExtra("searchQuery");
                displayFoodDetails(foodDetails, searchQuery);
            }

            @Override
            public void onError(String error) {
                resultTextView.setText(error);
            }
        });
    }

    private void displayFoodDetails(FoodDetailsModel foodDetails, String query) {
        String details = "Search Query: " + query + "\n\n";
        details += "Food: " + foodDetails.getLabel() + "\n";
        details += "Calories: " + foodDetails.getCalories() + "\n";
        details += "Fat (g): " + foodDetails.getFat() + "\n";
        details += "Protein (g): " + foodDetails.getProtein() + "\n";
        details += "Vitamins (%): " + foodDetails.getVitamins() + "\n";
        details += "Carbohydrates (g): " + foodDetails.getCarbohydrates() + "\n";
        details += "Cholesterol (mg): " + foodDetails.getCholesterol() + "\n";
        details += "Iron (%): " + foodDetails.getIron() + "\n";
        details += "Calcium (%): " + foodDetails.getCalcium() + "\n";

        resultTextView.setText(details);
    }
}