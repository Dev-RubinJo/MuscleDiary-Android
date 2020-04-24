package com.munchkin.musclediary.src.main.food.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.munchkin.musclediary.R;
import com.munchkin.musclediary.src.main.food.models.MenuItem;

import java.util.ArrayList;

public class MenuResultAdapter extends RecyclerView.Adapter<MenuResultAdapter.ViewHolder> {

    Context mContext;
    ArrayList<MenuItem> mMenuResult;

    public MenuResultAdapter(Context context, ArrayList<MenuItem> menuItems){
        mContext = context;
        mMenuResult = menuItems;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView menuTitle;
        TextView menuDescription;
        TextView menuCalories;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            menuTitle = (TextView) itemView.findViewById(R.id.input_menu_item_tv_menu_title);
            menuDescription = (TextView) itemView.findViewById(R.id.input_menu_item_tv_menu_description);
            menuCalories = (TextView) itemView.findViewById(R.id.input_menu_item_tv_menu_total_calories);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.item_result_of_menu,parent,false);
        MenuResultAdapter.ViewHolder viewHolder = new MenuResultAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MenuItem menuItem = mMenuResult.get(position);
        holder.menuTitle.setText(menuItem.getFoodName());
        holder.menuCalories.setText(menuItem.getTotalCal()+" kcal");
        holder.menuDescription.setText("api 연동시 자세한 설명이 들어갈 자리입니다.");
    }

    @Override
    public int getItemCount() {
        return mMenuResult.size();
    }

}
