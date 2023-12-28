package com.example.unipaths.Adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.unipaths.R;

public class LeaderboardAdapter extends RecyclerView.Adapter<LeaderboardAdapter.LeaderboardViewHolder> {

    @NonNull
    @Override
    public LeaderboardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull LeaderboardViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public static class LeaderboardViewHolder extends RecyclerView.ViewHolder {
        public TextView tvNumber,tvName,tvPoint;
        public ImageView ivProfilePic;

        public LeaderboardViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNumber = itemView.findViewById(R.id.tvNumber);
            tvName = itemView.findViewById(R.id.tvName);
            tvPoint=itemView.findViewById(R.id.tvPoint);
            ivProfilePic=itemView.findViewById(R.id.profilePic);
        }
    }
}
