package com.munchkin.musclediary.src.main.food.models;

import java.io.Serializable;
import java.util.ArrayList;

public class MealItem implements Serializable {

    private String mealTitle;
    private double mealTotalCalories;
    private ArrayList<MenuItem> menuItemList;

    public String getMealTitle() {
        return mealTitle;
    }

    public void setMealTitle(String mealTitle) {
        this.mealTitle = mealTitle;
    }

    public double getMealTotalCalories() {
        return mealTotalCalories;
    }

    public void setMealTotalCalories(double mealTotalCalories) {
        this.mealTotalCalories = mealTotalCalories;
    }

    public ArrayList<MenuItem> getMenuItemList() {
        return menuItemList;
    }

    public void setMenuItemList(ArrayList<MenuItem> menuItemList) {
        this.menuItemList = menuItemList;
    }

}
