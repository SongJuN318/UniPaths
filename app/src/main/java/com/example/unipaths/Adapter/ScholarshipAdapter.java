package com.example.unipaths.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.unipaths.Models.ScholarshipItem;
import com.example.unipaths.R;
import com.google.firebase.firestore.DocumentSnapshot;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ScholarshipAdapter extends RecyclerView.Adapter<ScholarshipAdapter.MyViewHolder> {

    static Context context;

    static ArrayList<ScholarshipItem> list;
    OnScholarshipItemClickListener onScholarshipItemClickListener;


    public ScholarshipAdapter(Context context, ArrayList<ScholarshipItem> list) {
        this.context = context;
        this.list = list;
    }

    public interface OnScholarshipItemClickListener{
        void onScholarshipItemClick(int position);
    }

    public void setOnScholarshipItemClickListener(OnScholarshipItemClickListener onScholarshipItemClickListener) {
        this.onScholarshipItemClickListener = onScholarshipItemClickListener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_scholarship,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        ScholarshipItem scholarshipItem = list.get(position);
        Log.d("ScholarshipAdapter", "Name: " + scholarshipItem.getItemName());
        holder.itemName.setText(scholarshipItem.getItemName());

        String ImageURL = null;
        ImageURL = scholarshipItem.getImageURL();
        Picasso.get().load(ImageURL).into(holder.imageView);

        holder.itemView.setOnClickListener(view ->{
            if (onScholarshipItemClickListener != null) {
                onScholarshipItemClickListener.onScholarshipItemClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView itemName;
        ImageView imageView;
        TextView itemDescription;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            itemName = itemView.findViewById(R.id.ScholarshipName);
            imageView = itemView.findViewById(R.id.scholarshipImage);
            itemDescription = itemView.findViewById(R.id.tvOrgDescription);

        }

    }


}
