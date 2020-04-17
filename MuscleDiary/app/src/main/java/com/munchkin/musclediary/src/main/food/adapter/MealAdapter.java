package com.munchkin.musclediary.src.main.food.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.munchkin.musclediary.R;
import com.munchkin.musclediary.src.main.food.models.MealItem;

import java.util.ArrayList;

public class MealAdapter extends RecyclerView.Adapter<MealAdapter.ViewHolder> {

    private Context mContext;
    private ArrayList<MealItem> mMealItems;

    public MealAdapter(Context context, ArrayList<MealItem> mealItems){
        mContext = context;
        mMealItems = mealItems;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvMealTitle;
        TextView tvTotalCalories;
        Button btnAddFood;
        ImageButton iBtnExpand;
        public ViewHolder(@NonNull View itemView){
            super(itemView);
            tvMealTitle = itemView.findViewById(R.id.fragment_food_item_tv_meal_title);
            tvTotalCalories = itemView.findViewById(R.id.fragment_food_item_tv_total_calories);
            btnAddFood = itemView.findViewById(R.id.fragment_food_item_btn_add_food);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.item_meal,parent,false);
        MealAdapter.ViewHolder viewHolder = new MealAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MealItem mealItem = mMealItems.get(position);
        holder.tvMealTitle.setText(mealItem.getMealTitle());
        holder.tvTotalCalories.setText(Double.toString(mealItem.getMealTotalCalories())+" kcal");
    }

    @Override
    public int getItemCount() {
        return mMealItems.size();
    }


}
