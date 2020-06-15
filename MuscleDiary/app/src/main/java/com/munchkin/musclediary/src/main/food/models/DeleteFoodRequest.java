package com.munchkin.musclediary.src.main.food.models;

import com.google.gson.annotations.SerializedName;

public class DeleteFoodRequest {
    @SerializedName("menuNo")
    int menuNo;

    public DeleteFoodRequest(int menuNo) {
        this.menuNo = menuNo;
    }
}
