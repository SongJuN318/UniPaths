package com.example.unipaths.Activities;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.unipaths.R;

import java.util.List;

public class PastResultsRecyclerViewAdapter extends RecyclerView.Adapter<PastResultsRecyclerViewAdapter.ViewHolder> {
    List<PastPersonality> personalityList;
    public PastResultsRecyclerViewAdapter(List<PastPersonality> personalityList){this.personalityList = personalityList;}

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_past_results, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        PastPersonality pastPersonality = personalityList.get(position);
        holder.careerName.setText(pastPersonality.getPersonalityType());
        holder.dateTaken.setText(pastPersonality.getDateTaken());
        holder.timeTaken.setText(pastPersonality.getTimeTaken());
    }

    @Override
    public int getItemCount() {
        return personalityList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private TextView careerName, dateTaken, timeTaken;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            careerName = itemView.findViewById(R.id.personality_name);
            dateTaken = itemView.findViewById(R.id.date_taken);
            timeTaken = itemView.findViewById(R.id.time_taken);
        }
    }
}
