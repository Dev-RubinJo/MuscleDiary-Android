package com.munchkin.musclediary.src.main.food.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.munchkin.musclediary.R;
import com.munchkin.musclediary.src.main.food.interfaces.MenuItemClickListener;
import com.munchkin.musclediary.src.main.food.models.MenuItem;

import java.util.ArrayList;

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.ViewHolder> {

    private Context mContext;
    private ArrayList<MenuItem> mMenuList;

    public MenuAdapter(Context context, ArrayList<MenuItem> menuList) {
        this.mContext = context;
        this.mMenuList = menuList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.item_menu,parent,false);
        MenuAdapter.ViewHolder viewHolder = new MenuAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tvMenuTitle.setText(mMenuList.get(position).getFoodName());
        holder.tvMenuCalories.setText(Double.toString(mMenuList.get(position).getTotalCal())+" kcal");

        holder.setmMenuItemClickListener(new MenuItemClickListener() {
            @Override
            public void onMenuClickListener(View view, int position) {
                //TODO : 나중에 클릭시 수정 가능하게 작성 (현재는 그냥 toast)
                Toast.makeText(mContext,mMenuList.get(position).getFoodName()+"이/가 눌렸습니다",Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return (mMenuList != null ? mMenuList.size():0);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvMenuTitle;
        TextView tvMenuCalories;
        Button btnDeleteMenu;

        MenuItemClickListener mMenuItemClickListener;

        public void setmMenuItemClickListener(MenuItemClickListener mMenuItemClickListener) {
            this.mMenuItemClickListener = mMenuItemClickListener;
        }

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvMenuTitle = (TextView) itemView.findViewById(R.id.fragment_food_item_tv_menu_title);
            tvMenuCalories = (TextView) itemView.findViewById(R.id.fragment_food_item_tv_menu_calories);
            btnDeleteMenu = (Button) itemView.findViewById(R.id.fragment_food_item_btn_delete_menu);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view){
            mMenuItemClickListener.onMenuClickListener(view,getAdapterPosition());
        }
    }
}
