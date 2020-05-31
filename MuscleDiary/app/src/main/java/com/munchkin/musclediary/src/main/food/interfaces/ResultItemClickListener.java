package com.munchkin.musclediary.src.main.food.interfaces;

import android.content.Intent;
import com.munchkin.musclediary.src.main.food.models.MenuItem;

public interface ResultItemClickListener {
    public void onResultItemClicked(Intent intentSending, MenuItem clickedMenu);
}
