package com.munchkin.musclediary.src.main.food.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.munchkin.musclediary.R;
import com.munchkin.musclediary.src.main.food.InputMenuActivity;
import com.munchkin.musclediary.src.main.food.models.MenuItem;

import java.util.ArrayList;

public class SelectedMenuAdapter extends RecyclerView.Adapter<SelectedMenuAdapter.ViewHolder> {

    Context mContext;
    ArrayList<MenuItem> mSelectedMenu;
    InputMenuActivity mInputMenuActivity;

    public SelectedMenuAdapter(Context context, ArrayList<MenuItem> selectedMenu, InputMenuActivity inputMenuActivity){
        mContext = context;
        mSelectedMenu = selectedMenu;
        mInputMenuActivity = inputMenuActivity;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView menuTitle;
        Button btnDelete;

        public ViewHolder(@NonNull final View itemView) {
            super(itemView);
            menuTitle = (TextView) itemView.findViewById(R.id.input_menu_item_tv_menu_add_list);
            btnDelete = (Button) itemView.findViewById(R.id.input_menu_item_btn_del_menu);

            btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mInputMenuActivity.onSelectedItemDelete(getAdapterPosition());
                }
            });
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.item_menu_add_list,parent,false);
        SelectedMenuAdapter.ViewHolder viewHolder = new SelectedMenuAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MenuItem selectedItem = mSelectedMenu.get(position);
        String title = selectedItem.getFoodName();
        if(title.length()>5){ title = title.substring(0,5)+".."; }
        holder.menuTitle.setText(title);
    }

    @Override
    public int getItemCount() {
        return mSelectedMenu.size();
    }

    public void changeDataset(ArrayList<MenuItem> newMenuItems){
        this.mSelectedMenu = newMenuItems;
        this.notifyDataSetChanged();
    }
}
