package com.munchkin.musclediary.src.main.setting;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.munchkin.musclediary.R;
import com.munchkin.musclediary.src.main.chart.ChartItem;

import java.util.ArrayList;

public class SettingAdapter extends RecyclerView.Adapter<SettingAdapter.ViewHolder> {

    private Context mContext;
    private ArrayList<SettingItem> mItems;

    SettingAdapter(Context context, ArrayList<SettingItem> items){
        mContext = context;
        mItems = items;
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvMeal;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvMeal = itemView.findViewById(R.id.tv_meal_setting_item);

        }
    }

    @Override
    public SettingAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.item_setting, parent, false);
        SettingAdapter.ViewHolder viewHolder = new SettingAdapter.ViewHolder(view);
        return viewHolder;
    }
    @Override
    public void onBindViewHolder(SettingAdapter.ViewHolder holder, int position) {
        SettingItem item = mItems.get(position);
        holder.tvMeal.setText(item.getMeal());
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }
}
