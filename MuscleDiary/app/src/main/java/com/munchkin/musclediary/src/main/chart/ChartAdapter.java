package com.munchkin.musclediary.src.main.chart;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.munchkin.musclediary.R;

import java.util.ArrayList;

public class ChartAdapter extends RecyclerView.Adapter<ChartAdapter.ViewHolder> {

    private Context mContext;
    private ArrayList<ChartItem> mItems;

    ChartAdapter(Context context, ArrayList<ChartItem> items){
        mContext = context;
        mItems = items;
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvLevel;
        TextView tvDate;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvLevel = itemView.findViewById(R.id.tv_level_chart_item);
            tvDate = itemView.findViewById(R.id.tv_date_chart_item);
        }
    }

    @Override
    public ChartAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.item_chart, parent, false);
        ChartAdapter.ViewHolder viewHolder = new ChartAdapter.ViewHolder(view);
        return viewHolder;
    }
    @Override
    public void onBindViewHolder(ChartAdapter.ViewHolder holder, int position) {
        ChartItem item = mItems.get(position);
        holder.tvLevel.setText(String.format("%.1fKg",item.getLevel()));
        holder.tvDate.setText(String.format("%d년 %d월 %d일", item.getYear(), item.getMonth(), item.getDate()));
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }
}
