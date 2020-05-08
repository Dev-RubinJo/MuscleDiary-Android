package com.munchkin.musclediary.src.main.exercise.adapter;

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
import com.munchkin.musclediary.src.main.exercise.models.ExerciseItem;

import java.util.ArrayList;

public class ExerciseAdapter extends RecyclerView.Adapter<ExerciseAdapter.ViewHolder> {

    private Context mContext;
    private ArrayList<ExerciseItem> mExerciseList;

    public ExerciseAdapter(Context context, ArrayList<ExerciseItem> exerciseList){
        this.mContext = context;
        this.mExerciseList = exerciseList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.item_exercise,parent,false);
        ExerciseAdapter.ViewHolder viewHolder = new ExerciseAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.tvExerciseTitle.setText(mExerciseList.get(position).getExeciseName());
        holder.tvExerciseDescription.setText(mExerciseList.get(position).getDescription());

        holder.btnDeleteExercise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext,mExerciseList.get(position).getExeciseName()+"을/를 삭제합니다",Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return (mExerciseList != null ? mExerciseList.size():0);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvExerciseTitle;
        TextView tvExerciseDescription;
        Button btnDeleteExercise;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvExerciseTitle = itemView.findViewById(R.id.fragment_exercise_item_tv_exercise_title);
            tvExerciseDescription = itemView.findViewById(R.id.fragment_exercise_item_tv_exercise_information);
            btnDeleteExercise = itemView.findViewById(R.id.fragment_exercise_item_btn_delete_exercise);
        }
    }
}
