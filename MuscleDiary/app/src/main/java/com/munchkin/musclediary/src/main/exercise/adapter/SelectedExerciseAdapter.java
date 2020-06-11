package com.munchkin.musclediary.src.main.exercise.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.munchkin.musclediary.R;
import com.munchkin.musclediary.src.main.exercise.models.ExerciseItem;

import java.util.ArrayList;

public class SelectedExerciseAdapter extends RecyclerView.Adapter<SelectedExerciseAdapter.ViewHolder> {

    Context mContext;
    ArrayList<ExerciseItem> mSelectedExercise;

    public SelectedExerciseAdapter(Context context, ArrayList<ExerciseItem> selectedExercise) {
        mContext = context;
        mSelectedExercise = selectedExercise;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView exerciseTitle;
        Button btnDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            exerciseTitle = (TextView) itemView.findViewById(R.id.input_exercise_item_tv_exercise_add_list);
            btnDelete = (Button) itemView.findViewById(R.id.input_exercise_item_btn_del_exercise);

            btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //TODO interface로 activity에서 DATA SET 바꿔주고 NOTIFY하기
                }
            });
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.item_exercise_add_list,parent,false);
        SelectedExerciseAdapter.ViewHolder viewHolder = new SelectedExerciseAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ExerciseItem selectedItem = mSelectedExercise.get(position);
        String title = selectedItem.getExeciseName();
        if(title.length()>5){ title = title.substring(0,5)+".."; }
        holder.exerciseTitle.setText(title);
    }

    @Override
    public int getItemCount() {
        return mSelectedExercise.size();
    }
}
