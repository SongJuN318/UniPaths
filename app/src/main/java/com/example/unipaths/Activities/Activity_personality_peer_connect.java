package com.example.unipaths.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.unipaths.Models.UserHelperClass;
import com.example.unipaths.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import kotlin.collections.ArrayDeque;

public class Activity_personality_peer_connect extends AppCompatActivity {
    private RecyclerView recyclerView;
    private PeersRecyclerViewAdapter adapter;
    private DatabaseReference currentUserRef;
    private List<UserHelperClass> userList;
    private String userPersonality, userId;
    private List<Peer> peerList;
    private List<String> idList;
    private Button backBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personality_peer_connect);
        backBtn = findViewById(R.id.back_icon);
        recyclerView = findViewById(R.id.recycler_view_peers);
        peerList = new ArrayList<>();
        idList = new ArrayList<>();
        userList = new ArrayList<>();
        userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        currentUserRef = FirebaseDatabase.getInstance().getReference().child("users").child(userId);

        getUserPersonality();
        adapter = new PeersRecyclerViewAdapter(this, peerList);
        //Connect RV to adapter
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void getUserPersonality(){
        DatabaseReference personalityRef = currentUserRef.child("personality");
        personalityRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                userPersonality = snapshot.getValue(String.class);
                getOtherUserId();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void getOtherUserId(){
        DatabaseReference otherUserRef = FirebaseDatabase.getInstance().getReference().child("users");
        otherUserRef.orderByChild("personality").equalTo(userPersonality)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists()){
                            if (!idList.isEmpty()){
                                idList.clear();
                            }
                            int count = 0;
                            for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                                String otherUserId = dataSnapshot.getKey();
                                idList.add(otherUserId);
                                count++;

                                if (count>20){
                                    break;
                                }
                            }
                            showPeers();
                        }
                        else{}
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }

    private void showPeers(){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("users");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                peerList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    for (String id : idList){
                        String userId = dataSnapshot.getKey();
                        if (userId.equals(id)){
                            String name = dataSnapshot.child("name").getValue(String.class);
                            String personality = dataSnapshot.child("personality").getValue(String.class);
                            String imageurl = dataSnapshot.child("imageurl").getValue(String.class);
                            peerList.add(new Peer(name, personality, imageurl, userId));
                        }
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    
}