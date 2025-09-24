package com.example.wellness360;

import android.content.Context;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class FoodDetailsRequest {

    public interface FoodDetailsListener {
        void onFoodDetailsReceived(FoodDetailsModel foodDetails);
        void onError(String error);
    }

   
    public static void fetchFoodDetails(Context context, String foodQuery, FoodDetailsListener listener) {
        String url = BASE_URL + "?ingr=" + foodQuery + "&app_id=" + API_ID + "&app_key=" + API_KEY;

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                response -> {
                    try {
                        JSONArray hints = response.getJSONArray("hints");
                        if (hints.length() > 0) {
                            JSONObject food = hints.getJSONObject(0).getJSONObject("food");
                            FoodDetailsModel foodDetails = parseFoodDetails(food);
                            listener.onFoodDetailsReceived(foodDetails);
                        } else {
                            listener.onError("No details found for this food.");
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        listener.onError("Error parsing data.");
                    }
                },
                error -> listener.onError("Network error. Please try again later."));

        Volley.newRequestQueue(context).add(request);
    }

    private static FoodDetailsModel parseFoodDetails(JSONObject food) throws JSONException {
        FoodDetailsModel foodDetails = new FoodDetailsModel();
        foodDetails.setLabel(food.optString("label"));
        JSONObject nutrients = food.getJSONObject("nutrients");
        foodDetails.setCalories(nutrients.optDouble("ENERC_KCAL", 0));
        foodDetails.setFat(nutrients.optDouble("FAT", 0));
        foodDetails.setProtein(nutrients.optDouble("PROCNT", 0));
        foodDetails.setVitamins(nutrients.optDouble("VITA_IU", 0));
        foodDetails.setCarbohydrates(nutrients.optDouble("CHOCDF", 0));
        foodDetails.setCholesterol(nutrients.optDouble("CHOLE", 0));
        foodDetails.setIron(nutrients.optDouble("FE", 0));
        foodDetails.setCalcium(nutrients.optDouble("CA", 0));

        return foodDetails;
    }
}
