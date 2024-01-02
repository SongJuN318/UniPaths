package com.example.unipaths.Activities;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.unipaths.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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
        String imagePath = career.getCareer_icon();
        String careerDesc = career.getCareer_des();
        Log.d("TAG", "Image path: "+imagePath);
        Glide.with(holder.itemView.getContext())
                .load(imagePath)
                        .into(holder.img);
        holder.career_name.setText(career.getCareer_name());
        Log.d("TAG", "Bind OK");
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PersonalityDialogFragment personalityDialogFragment = new PersonalityDialogFragment(careerDesc);
                FragmentManager fragmentManager = ((AppCompatActivity) holder.itemView.getContext()).getSupportFragmentManager();
                personalityDialogFragment.show(fragmentManager, "Career Description");
            }
        });
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
