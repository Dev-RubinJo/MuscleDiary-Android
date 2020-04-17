package com.munchkin.musclediary.src.main.food.models;

public class MenuItem {

    private String foodName;
    private double fatGram;
    private double proteinGram;
    private double carboGram;
    private double totalCal;

    public MenuItem(String foodName, double fatGram,
                    double proteinGram, double carboGram, double totalCal){
        this.foodName = foodName;
        this.fatGram = fatGram;
        this.proteinGram = proteinGram;
        this.carboGram = carboGram;
        this.totalCal = totalCal;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public void setFatGram(double fatGram) {
        this.fatGram = fatGram;
    }

    public void setProteinGram(double proteinGram) {
        this.proteinGram = proteinGram;
    }

    public void setCarboGram(double carboGram) {
        this.carboGram = carboGram;
    }

    public void setTotalCal(double totalCal) {
        this.totalCal = totalCal;
    }

    public String getFoodName() {
        return foodName;
    }

    public double getFatGram() {
        return fatGram;
    }

    public double getProteinGram() {
        return proteinGram;
    }

    public double getCarboGram() {
        return carboGram;
    }

    public double getTotalCal() {
        return totalCal;
    }

}
