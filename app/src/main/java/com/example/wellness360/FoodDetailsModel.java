package com.example.wellness360;

public class FoodDetailsModel {
    private String label;
    private double calories;
    private double fat;
    private double protein;
    private double vitamins;
    private double carbohydrates;
    private double cholesterol;
    private double iron;
    private double calcium;
    public void setLabel(String label) {
        this.label = label;
    }
    public void setCalories(double calories) {
        this.calories = calories;
    }

    public void setFat(double fat) {
        this.fat = fat;
    }
    public void setProtein(double protein) {
        this.protein = protein;
    }
    public void setVitamins(double vitamins) {
        this.vitamins = vitamins;
    }
    public void setCarbohydrates(double carbohydrates) {
        this.carbohydrates = carbohydrates;
    }
    public void setCholesterol(double cholesterol) {
        this.cholesterol = cholesterol;
    }
    public void setIron(double iron) {
        this.iron = iron;
    }
    public void setCalcium(double calcium) {
        this.calcium = calcium;
    }

    public String getLabel() {
        return label;
    }
    public double getCalories() {
        return calories;
    }
    public double getFat() {
        return fat;
    }
    public double getProtein() {
        return protein;
    }
    public double getVitamins() {
        return vitamins;
    }
    public double getCarbohydrates() {
        return carbohydrates;
    }
    public double getCholesterol() {
        return cholesterol;
    }
    public double getIron() {
        return iron;
    }
    public double getCalcium() {
        return calcium;
    }
}
