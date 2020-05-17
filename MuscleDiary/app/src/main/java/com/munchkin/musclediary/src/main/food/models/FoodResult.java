package com.munchkin.musclediary.src.main.food.models;

import com.google.gson.annotations.SerializedName;

public class FoodResult {
    @SerializedName("foodNo")
    private int foodNo;

    @SerializedName("foodGroup")
    private String foodGroup;

    @SerializedName("foodName")
    private String foodName;

    @SerializedName("foodRegion")
    private String foodRegion;

    @SerializedName("onetimeSupply")
    private int onetimeSupply;

    @SerializedName("calorie")
    private double calorie;

    @SerializedName("carbohydrate")
    private double carbohydrate;

    @SerializedName("protein")
    private double protein;

    @SerializedName("fat")
    private double fat;

    @SerializedName("sugar")
    private double sugar;

    @SerializedName("sodium")
    private double sodium;

    @SerializedName("cholesterol")
    private double cholesterol;

    @SerializedName("saturatedFat")
    private double saturatedFat;

    @SerializedName("transFat")
    private double transFat;

    public int getfoodNo() {
        return foodNo;
    }

    public String getFoodGroup() {
        return foodGroup;
    }

    public String getFoodName() {
        return foodName;
    }

    public String getFoodRegion() {
        return foodRegion;
    }

    public int getOnetimeSupply() {
        return onetimeSupply;
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

    public double getSugar() {
        return sugar;
    }

    public double getSodium() {
        return sodium;
    }

    public double getCholesterol() {
        return cholesterol;
    }

    public double getSaturatedFat() {
        return saturatedFat;
    }

    public double getTransFat() {
        return transFat;
    }
}
