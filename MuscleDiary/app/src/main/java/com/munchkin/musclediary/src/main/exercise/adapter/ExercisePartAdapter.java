package com.munchkin.musclediary.src.main.exercise.adapter;

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
import com.munchkin.musclediary.src.main.exercise.ArrangeExerciseActivity;
import com.munchkin.musclediary.src.main.exercise.decrypted_InputExerciseActivity;
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
                //NOTE 원래 inputExercise로 연결했으나 구조수정으로 인해 바로 ArrangeExerciseActivity로 이동
                Intent inputExerciseIntent = new Intent(mContext, ArrangeExerciseActivity.class);
                inputExerciseIntent.putExtra("exercisePartTitle",exercisePartItem.getExercisePartTitle());
                //Fragment로 다시 돌아올 때 activity에서 fragment로 값전달 할 수 있도록 하는 스텝1 : casting 해서 forResult로 호출
                ((MainActivity)mContext).startActivityForResult(inputExerciseIntent,2000);
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
