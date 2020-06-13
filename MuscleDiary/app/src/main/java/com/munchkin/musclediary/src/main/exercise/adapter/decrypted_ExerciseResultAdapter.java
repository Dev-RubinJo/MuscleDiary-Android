package com.munchkin.musclediary.src.main.exercise.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.munchkin.musclediary.R;
import com.munchkin.musclediary.src.main.exercise.ArrangeExerciseActivity;
import com.munchkin.musclediary.src.main.exercise.interfaces.ResultExerciseItemClickListener;
import com.munchkin.musclediary.src.main.exercise.models.ExerciseItem;

import java.util.ArrayList;

//NOTE Adapter decrtpted

public class decrypted_ExerciseResultAdapter extends RecyclerView.Adapter<decrypted_ExerciseResultAdapter.ViewHolder> {

    Context mContext;
    ArrayList<ExerciseItem> mExerciseResult;
    private ResultExerciseItemClickListener startActivityForResultInterface;
    int mItemPosition;

    public decrypted_ExerciseResultAdapter(Context context, ArrayList<ExerciseItem> exerciseItems, ResultExerciseItemClickListener listener) {
        mContext = context;
        mExerciseResult = exerciseItems;
        this.startActivityForResultInterface = listener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView exerciseTitle;
        TextView exerciseDescription;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            exerciseTitle = itemView.findViewById(R.id.input_exercise_item_tv_exercise_title);
            exerciseDescription = itemView.findViewById(R.id.input_exercise_item_tv_exercise_description);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mItemPosition = getAdapterPosition();
                    ExerciseItem clickedItem = mExerciseResult.get(mItemPosition);
                    Intent arrangeExerciseIntent = new Intent(mContext, ArrangeExerciseActivity.class);
                    arrangeExerciseIntent.putExtra("exerciseName",exerciseTitle.getText().toString());
                    arrangeExerciseIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);

                    startActivityForResultInterface.onResultItemClicked(arrangeExerciseIntent,clickedItem);
                }
            });
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.item_result_of_exercise,parent,false);
        decrypted_ExerciseResultAdapter.ViewHolder viewHolder = new decrypted_ExerciseResultAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ExerciseItem exerciseItem = mExerciseResult.get(position);
        holder.exerciseTitle.setText(exerciseItem.getExeciseName());
        /*
        TODO : 혹여나 설명이 들어가게 되면 여기에다 쓰면 됨
        holder.exerciseDescription.setText();
        */
    }

    @Override
    public int getItemCount() {
        return mExerciseResult.size();
    }
}
