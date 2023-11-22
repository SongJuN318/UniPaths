package com.example.unipaths.Activities;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.unipaths.R;

import java.util.List;

public class Know_Require_CuntomAdapter extends RecyclerView.Adapter<Know_Require_CuntomAdapter.ViewHolder>{
    private List<Item_requirement> items;

    public Know_Require_CuntomAdapter(List<Item_requirement> items) {
        this.items = items;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_requirement, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Item_requirement item = items.get(position);
        holder.subtitle.setText(item.getSubtitle());
        holder.content.setText(item.getContent());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView subtitle;
        public TextView content;

        public ViewHolder(View itemView) {
            super(itemView);
            subtitle = itemView.findViewById(R.id.subtitle);
            content = itemView.findViewById(R.id.content);
        }
    }
}
