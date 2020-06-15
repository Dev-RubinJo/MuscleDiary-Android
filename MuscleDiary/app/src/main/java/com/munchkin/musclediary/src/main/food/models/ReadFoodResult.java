package com.munchkin.musclediary.src.main.food.models;

import com.google.gson.annotations.SerializedName;

public class ReadFoodResult {
    @SerializedName("menuNo")
    int menuNo;
    @SerializedName("foodName")
    String foodName;
    @SerializedName("calorie")
    double calorie;
    @SerializedName("carbohydrate")
    double carbohydrate;
    @SerializedName("protein")
    double protein;
    @SerializedName("fat")
    double fat;
    @SerializedName("serving")
    double serving;
    @SerializedName("foodRegion")
    String foodRegion;

    public int getMenuNo() {
        return menuNo;
    }

    public String getFoodName() {
        return foodName;
    }

    public double getCalorie() {
        return calorie;
    }

    public double getCarbohydrate() {
        return carbohydrate;
    }

    public double getProtein() {
        return protein;
    }

    public double getFat() {
        return fat;
    }

    public double getServing() {
        return serving;
    }

    public String getFoodRegion() {
        return foodRegion;
    }
}
