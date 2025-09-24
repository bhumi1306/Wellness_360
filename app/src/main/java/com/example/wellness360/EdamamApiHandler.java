package com.example.wellness360;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
public class EdamamApiHandler {

//
//    public static void getCaloriesAsync(String[] foodNames, OnCaloriesReceivedListener listener) {
//        new AsyncTask<Void, Void, Integer>() {
//            @Override
//            protected Integer doInBackground(Void... voids) {
//                int totalCalories = 0;
//                for (String foodName : foodNames) {
//                    int calories = 0;
//                    try {
//                        URL url = new URL(API_URL + "?ingr=" + foodName + "&app_id=" + APP_ID + "&app_key=" + APP_KEY);
//                        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//                        connection.setRequestMethod("GET");
//
//                        int responseCode = connection.getResponseCode();
//                        if (responseCode == HttpURLConnection.HTTP_OK) {
//                            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
//                            StringBuilder response = new StringBuilder();
//                            String line;
//                            while ((line = reader.readLine()) != null) {
//                                response.append(line);
//                            }
//                            reader.close();
//
//                            calories = extractCaloriesFromResponse(response.toString());
//                        }
//                        connection.disconnect();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                    totalCalories += calories;
//                }
//                return totalCalories;
//            }
//
//            @Override
//            protected void onPostExecute(Integer totalCalories) {
//                listener.onCaloriesReceived(totalCalories);
//            }
//        }.execute();
//    }
//
//    private static int extractCaloriesFromResponse(String jsonResponse) {
//        int calories = 0;
//        try {
//            JSONObject jsonObject = new JSONObject(jsonResponse);
//            JSONArray parsed = jsonObject.getJSONArray("parsed");
//
//            if (parsed.length() > 0) {
//                JSONObject food = parsed.getJSONObject(0).getJSONObject("food");
//                calories = food.getJSONObject("nutrients").getInt("ENERC_KCAL");
//            }
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        return calories;
//    }
//
//    public interface OnCaloriesReceivedListener {
//        void onCaloriesReceived(int calories);
//    }
}
