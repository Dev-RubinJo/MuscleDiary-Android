package com.munchkin.musclediary.src.main.food.interfaces;

import android.view.View;

public interface MenuItemClickListener {
    void onMenuClickListener(View view, int position);
    void onMenuDeleteClicked(int menuNo);
}
