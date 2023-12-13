package com.example.unipaths.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.unipaths.Activities.QuizItem;
import com.example.unipaths.R;

import java.util.List;

public class QuizAdapter extends RecyclerView.Adapter<QuizAdapter.QuizViewHolder> {
    private Context context;
    private List<QuizItem> quizItemList;

    public QuizAdapter(Context context, List<QuizItem> quizItemList) {
        this.context = context;
        this.quizItemList = quizItemList;
    }

    @NonNull
    @Override
    public QuizViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_quiz, parent, false);
        return new QuizViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull QuizViewHolder holder, int position) {
        QuizItem quizItem = quizItemList.get(position);

        holder.tvQuizName.setText(quizItem.getQuizName());
        // Load image using Glide library
        Glide.with(context)
                .load(quizItem.getImageResourceUrl())
                .into(holder.ivQuizImage);
    }

    @Override
    public int getItemCount() {
        return quizItemList.size();
    }

    public static class QuizViewHolder extends RecyclerView.ViewHolder {
        public TextView tvQuizName;
        public ImageView ivQuizImage;

        public QuizViewHolder(@NonNull View itemView) {
            super(itemView);
            tvQuizName = itemView.findViewById(R.id.tvQuizName);
            ivQuizImage = itemView.findViewById(R.id.quizListImage);
        }
    }
}
