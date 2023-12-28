package com.example.unipaths.Activities;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.unipaths.R;

import java.util.ArrayList;
import java.util.List;

public class Know_Uni_CustomAdapter extends RecyclerView.Adapter<Know_Uni_CustomAdapter.ViewHolder> {

    private List<Item_universities> items;
    private List<Item_universities> originalItems;
    private Context context;

    public Know_Uni_CustomAdapter(Context context, List<Item_universities> items) {
        this.context = context;
        this.originalItems = new ArrayList<>(items);
        this.items = new ArrayList<>(items);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_university, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Item_universities item = items.get(position);
        holder.tvName.setText(item.getData());
        holder.ivImage.setImageResource(item.getImage());

        holder.tvName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(item.getUrl()));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String charString = constraint.toString();
                if (charString.isEmpty()) {
                    items = originalItems;
                } else {
                    List<Item_universities> filteredList = new ArrayList<>();
                    for (Item_universities row : originalItems) {
                        // here we are looking for name match
                        if (row.getData().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(row);
                        }
                    }
                    items = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = items;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                items = (ArrayList<Item_universities>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvName;
        public ImageView ivImage;

        public ViewHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.text_view);
            ivImage = itemView.findViewById(R.id.image_view);
        }
    }
}