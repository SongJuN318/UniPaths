package com.example.unipaths.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.unipaths.Models.UserHelperClass;
import com.example.unipaths.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class LeaderboardAdapter extends RecyclerView.Adapter<LeaderboardAdapter.ViewHolder> {
    private List<UserHelperClass> leaderboard;

    public LeaderboardAdapter(List<UserHelperClass> leaderboard) {
        this.leaderboard = leaderboard;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_leaderboard, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        UserHelperClass user = leaderboard.get(position);

        // Update the UI elements in the ViewHolder with user data
        holder.tvNumber.setText(String.valueOf(position + 1));
        holder.tvName.setText(user.getName());
        holder.tvPoint.setText(String.valueOf(user.getScore()));
        Picasso.get().load(user.getImageurl()).into(holder.ivProfilePic);
    }

    @Override
    public int getItemCount() {
        return leaderboard.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvNumber, tvName, tvPoint;
        ImageView ivProfilePic;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNumber = itemView.findViewById(R.id.tvNumber);
            tvName = itemView.findViewById(R.id.tvName);
            tvPoint = itemView.findViewById(R.id.tvPoint);
            ivProfilePic = itemView.findViewById(R.id.profilePic);
        }
    }
}
