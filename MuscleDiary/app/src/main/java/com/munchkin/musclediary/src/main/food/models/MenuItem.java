package com.munchkin.musclediary.src.main.food.models;

public class MenuItem {

    private int foodNo;
    private String foodGroup;
    private String foodName;
    private String foodRegion;
    private int onetimeSupply;
    private double calorie;
    private double carbohydrate;
    private double protein;
    private double fat;
    private double sugar;
    private double sodium;
    private double cholesterol;
    private double saturatedFat;
    private double transFat;

    public MenuItem(String foodName, double fat,
                    double protein, double carbohydrate, double calorie){
        this.foodName = foodName;
        this.fat = fat;
        this.protein = protein;
        this.carbohydrate = carbohydrate;
        this.calorie = calorie;
    }

    public MenuItem(FoodResult foodResult){
        foodNo = foodResult.getfoodNo();
        foodGroup = foodResult.getFoodGroup();
        foodName = foodResult.getFoodName();
        foodRegion = foodResult.getFoodRegion();
        onetimeSupply = foodResult.getOnetimeSupply();
        calorie = foodResult.getCalorie();
        carbohydrate = foodResult.getCarbohydrate();
        protein = foodResult.getProtein();
        fat = foodResult.getFat();
        sugar = foodResult.getSugar();
        sodium = foodResult.getSodium();
        cholesterol = foodResult.getCholesterol();
        saturatedFat = foodResult.getSaturatedFat();
        transFat = foodResult.getTransFat();
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public void setFat(double fat) {
        this.fat = fat;
    }

    public void setProtein(double protein) {
        this.protein = protein;
    }

    public void setCarbohydrate(double carbohydrate) {
        this.carbohydrate = carbohydrate;
    }

    public void setCalorie(double calorie) {
        this.calorie = calorie;
    }

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
