package com.munchkin.musclediary.src.main.food.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.munchkin.musclediary.R;
import com.munchkin.musclediary.src.main.MainActivity;
import com.munchkin.musclediary.src.main.food.InputMenuActivity;
import com.munchkin.musclediary.src.main.food.models.MealItem;
import com.munchkin.musclediary.src.main.food.models.MenuItem;

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

        RecyclerView rvMenuList;

        public ViewHolder(@NonNull View itemView){
            super(itemView);
            tvMealTitle = (TextView) itemView.findViewById(R.id.fragment_food_item_tv_menu_title);
            tvTotalCalories = (TextView) itemView.findViewById(R.id.fragment_food_item_tv_menu_calories);
            btnAddFood = (Button) itemView.findViewById(R.id.fragment_food_item_btn_delete_menu);

            rvMenuList = (RecyclerView) itemView.findViewById(R.id.fragmetn_food_item_rv_menu);
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
        final MealItem mealItem = mMealItems.get(position);
        holder.tvMealTitle.setText(mealItem.getMealTitle());
        holder.tvTotalCalories.setText(String.format("%.2f",mealItem.getMealTotalCalories())+" kcal");

        holder.btnAddFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent inputMenuIntent = new Intent(mContext, InputMenuActivity.class);
                inputMenuIntent.putExtra("mealTitle",mealItem.getMealTitle());
                //Fragment로 다시 돌아올 때 activity에서 fragment로 값전달 할 수 있도록 하는 스텝1 : casting 해서 forResult로 호출
                ((MainActivity)mContext).startActivityForResult(inputMenuIntent,1000);
            }
        });

        //끼니 리사이클러 뷰 속 메뉴 리사이클러 뷰 정의 - 어뎁터 생성,등록 등등
        ArrayList<MenuItem> menuItems = mMealItems.get(position).getMenuItemList();
        MenuAdapter menuAdapter = new MenuAdapter(mContext, menuItems);
        holder.rvMenuList.setHasFixedSize(true);
        holder.rvMenuList.setLayoutManager(new LinearLayoutManager(mContext,LinearLayoutManager.VERTICAL,false));
        holder.rvMenuList.setAdapter(menuAdapter);
        holder.rvMenuList.setNestedScrollingEnabled(false); // 중요
    }

    @Override
    public int getItemCount() {
        return mMealItems.size();
    }


}
