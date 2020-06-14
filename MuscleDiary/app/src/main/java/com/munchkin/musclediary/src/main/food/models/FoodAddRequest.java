package com.munchkin.musclediary.src.main.food.models;

import com.google.gson.annotations.SerializedName;

public class FoodAddRequest {
    @SerializedName("foodName")
    String foodName;
    @SerializedName("calorie")
    double calorie;
    @SerializedName("carbohydarte")
    double carbohydarte;
    @SerializedName("protein")
    double protein;
    @SerializedName("fat")
    double fat;
    @SerializedName("recordDate")
    String recordDate;
    @SerializedName("mealType")
    int mealType;
    @SerializedName("serving")
    double serving;
    @SerializedName("foodRegion")
    String foodRegion;

    public FoodAddRequest(String foodName, double calorie, double carbohydarte, double protein, double fat, String recordDate, int mealType, double serving, String foodRegion) {
        this.foodName = foodName;
        this.calorie = calorie;
        this.carbohydarte = carbohydarte;
        this.protein = protein;
        this.fat = fat;
        this.recordDate = recordDate;
        this.mealType = mealType;
        this.serving = serving;
        this.foodRegion = foodRegion;
    }
}
