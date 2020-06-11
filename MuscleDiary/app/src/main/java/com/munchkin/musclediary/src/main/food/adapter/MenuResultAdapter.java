package com.munchkin.musclediary.src.main.food.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.munchkin.musclediary.R;
import com.munchkin.musclediary.src.main.food.ArrangeMenuActivity;
import com.munchkin.musclediary.src.main.food.interfaces.ResultMenuItemClickListener;
import com.munchkin.musclediary.src.main.food.models.MenuItem;

import java.util.ArrayList;

public class MenuResultAdapter extends RecyclerView.Adapter<MenuResultAdapter.ViewHolder> {

    Context mContext;
    ArrayList<MenuItem> mMenuResult;
    private ResultMenuItemClickListener startActivityForResultInterface;
    int mItemPosition;


    public MenuResultAdapter(Context context, ArrayList<MenuItem> menuItems, ResultMenuItemClickListener listener){
        mContext = context;
        mMenuResult = menuItems;
        this.startActivityForResultInterface = listener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView menuTitle;
        TextView menuDescription;
        TextView menuCalories;

        public ViewHolder(@NonNull final View itemView) {
            super(itemView);
            menuTitle = (TextView) itemView.findViewById(R.id.input_menu_item_tv_menu_title);
            menuDescription = (TextView) itemView.findViewById(R.id.input_menu_item_tv_menu_description);
            menuCalories = (TextView) itemView.findViewById(R.id.input_menu_item_tv_menu_total_calories);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mItemPosition = getAdapterPosition();
                    MenuItem clickedItem = mMenuResult.get(mItemPosition);
                    double itemTotalGram = clickedItem.getCarbohydrate()+clickedItem.getFat()+clickedItem.getProtein();

                    Intent arrangeMenuIntent = new Intent(mContext, ArrangeMenuActivity.class);
                    arrangeMenuIntent.putExtra("menuName",menuTitle.getText().toString());
                    arrangeMenuIntent.putExtra("menuGramPerServe",itemTotalGram);
                    arrangeMenuIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);

                    startActivityForResultInterface.onResultItemClicked(arrangeMenuIntent,clickedItem);
                }
            });
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
        String kcal = menuItem.getCalorie()+" kcal";
        holder.menuCalories.setText(kcal);
        String info = "탄수화물-" + menuItem.getCarbohydrate() + "g  " + "단백질-" + menuItem.getProtein() + "g  " + "지방-" + menuItem.getFat() + "g";
        holder.menuDescription.setText(info);
    }

    @Override
    public int getItemCount() {
        return mMenuResult.size();
    }

}
