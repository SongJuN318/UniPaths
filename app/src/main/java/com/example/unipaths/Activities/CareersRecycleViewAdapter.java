package com.example.unipaths.Activities;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.unipaths.R;

import java.util.List;

public class CareersRecycleViewAdapter extends RecyclerView.Adapter<CareersRecycleViewAdapter.ViewHolder> {
    List<Career> careerList;
    public CareersRecycleViewAdapter(List<Career> careerList) {
        this.careerList = careerList;
    }

    @NonNull
    @Override
    public CareersRecycleViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_careers, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CareersRecycleViewAdapter.ViewHolder holder, int position) {
        Career career = careerList.get(position);
        holder.img.setImageResource(career.getCareer_icon());
        holder.career_name.setText(career.getCareer_name());
    }

    @Override
    public int getItemCount() {
        return careerList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView img;
        private TextView career_name;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            img = itemView.findViewById(R.id.career_icon);
            career_name = itemView.findViewById(R.id.career_name);
        }
    }
}
