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

public class PeersRecyclerViewAdapter extends RecyclerView.Adapter<PeersRecyclerViewAdapter.ViewHolder> {
    private List<Peer> peerList;

    public PeersRecyclerViewAdapter(List<Peer> peerList){
        this.peerList = peerList;
    }
    @NonNull
    @Override
    public PeersRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_peers, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PeersRecyclerViewAdapter.ViewHolder holder, int position) {
        Peer peer = peerList.get(position);
        holder.peerName.setText(peer.getPeerName());
        holder.peerDes.setText(peer.getPeerDes());
        holder.img.setImageResource(peer.getProfilePicId());
    }

    @Override
    public int getItemCount() {
        return peerList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView img;
        private TextView peerName;
        private TextView peerDes;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            img = itemView.findViewById(R.id.peer_profile_pic);
            peerName = itemView.findViewById(R.id.peer_name);
            peerDes = itemView.findViewById(R.id.peer_des);
        }
    }
}
