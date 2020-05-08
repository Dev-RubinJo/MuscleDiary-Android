package com.munchkin.musclediary.src.main.exercise.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.munchkin.musclediary.R;
import com.munchkin.musclediary.src.main.exercise.models.ExerciseItem;
import com.munchkin.musclediary.src.main.exercise.models.ExercisePartItem;

import java.util.ArrayList;

public class ExercisePartAdapter extends RecyclerView.Adapter<ExercisePartAdapter.ViewHolder> {

    private Context mContext;
    private ArrayList<ExercisePartItem> mExercisePartItems;

    public ExercisePartAdapter(Context context, ArrayList<ExercisePartItem> exercisePartItems){
        mContext = context;
        mExercisePartItems = exercisePartItems;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvExerciseTitle;
        Button btnAddFood;

        RecyclerView rvExeciseList;

        public ViewHolder(@NonNull View itemView){
            super(itemView);
            tvExerciseTitle = (TextView) itemView.findViewById(R.id.fragment_exercise_item_tv_exercise_part_title);
            btnAddFood = (Button) itemView.findViewById(R.id.fragment_exercise_item_btn_add_exercise);

            rvExeciseList = (RecyclerView) itemView.findViewById(R.id.fragment_exercise_item_rv_exercise);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.item_exercise_part,parent,false);
        ExercisePartAdapter.ViewHolder viewHolder = new ExercisePartAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final ExercisePartItem exercisePartItem = mExercisePartItems.get(position);
        holder.tvExerciseTitle.setText(exercisePartItem.getExercisePartTitle());
        holder.btnAddFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent inputMenuIntent = new Intent(mContext, InputMenuActivity.class);
//                inputMenuIntent.putExtra("mealTitle",mealItem.getMealTitle());
//                mContext.startActivity(inputMenuIntent);
            }
        });

        //끼니 리사이클러 뷰 속 메뉴 리사이클러 뷰 정의 - 어뎁터 생성,등록 등등
        ArrayList<ExerciseItem> exerciseItems = mExercisePartItems.get(position).getExerciseItemList();
        ExerciseAdapter exerciseAdapter = new ExerciseAdapter(mContext, exerciseItems);
        holder.rvExeciseList.setHasFixedSize(true);
        holder.rvExeciseList.setLayoutManager(new LinearLayoutManager(mContext,LinearLayoutManager.VERTICAL,false));
        holder.rvExeciseList.setAdapter(exerciseAdapter);
        holder.rvExeciseList.setNestedScrollingEnabled(false); // 중요

    }

    @Override
    public int getItemCount() {
        return mExercisePartItems.size();
    }


}
