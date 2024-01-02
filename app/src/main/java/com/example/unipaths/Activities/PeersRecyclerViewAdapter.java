package com.example.unipaths.Activities;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.unipaths.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class PeersRecyclerViewAdapter extends RecyclerView.Adapter<PeersRecyclerViewAdapter.ViewHolder> {
    private List<Peer> peerList;
    private Context context;
    private FirebaseUser firebaseUser;
    private DatabaseReference followRef;

    public PeersRecyclerViewAdapter(Context context, List<Peer> peerList){
        this.context = context;
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
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        followRef = FirebaseDatabase.getInstance().getReference().child("Follow");
        Peer peer = peerList.get(position);
        holder.peerName.setText(peer.getPeerName());
        holder.peerPers.setText(peer.getPersonality());
        Glide.with(context).load(peer.getImageurl()).into(holder.circleImageView);
        isFollowing(peer.getUserId(), holder.followBtn);

        if (peer.getUserId().equals(firebaseUser.getUid())){
            holder.followBtn.setVisibility(View.GONE);
        }

        holder.followBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.followBtn.getText().toString().equals("Follow")){
                    followRef.child(firebaseUser.getUid()).child("following").child(peer.getUserId()).setValue(true);
                    followRef.child(peer.getUserId()).child("followers").child(firebaseUser.getUid()).setValue(true);
                }
                else {
                    followRef.child(firebaseUser.getUid()).child("following").child(peer.getUserId()).removeValue();
                    followRef.child(peer.getUserId()).child("followers").child(firebaseUser.getUid()).removeValue();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return peerList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private CircleImageView circleImageView;
        private TextView peerName;
        private TextView peerPers;
        private Button followBtn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            circleImageView = itemView.findViewById(R.id.peer_profile_pic);
            peerName = itemView.findViewById(R.id.peer_name);
            peerPers = itemView.findViewById(R.id.peer_personality);
            followBtn = itemView.findViewById(R.id.follow_btn);
        }
    }

    private void isFollowing(String userId, Button followBtn){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Follow").child(firebaseUser.getUid()).child("following");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.child(userId).exists()){
                    followBtn.setText("Following");
                    followBtn.setTextColor(context.getColor(R.color.blue));
                    followBtn.setBackgroundResource(android.R.color.transparent);
                }else {
                    followBtn.setText("Follow");
                    followBtn.setTextColor(context.getColor(R.color.white));
                    followBtn.setBackgroundResource(R.drawable.blue_btn);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
