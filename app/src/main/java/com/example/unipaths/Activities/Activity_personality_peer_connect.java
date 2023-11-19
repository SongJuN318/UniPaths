package com.example.unipaths.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.unipaths.R;

import java.util.ArrayList;
import java.util.List;

import kotlin.collections.ArrayDeque;

public class Activity_personality_peer_connect extends AppCompatActivity {
    private RecyclerView recyclerView;
    private PeersRecyclerViewAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personality_peer_connect);

        recyclerView = findViewById(R.id.recycler_view_peers);
        List<Peer> peerList = generateDummyPeers();
        adapter = new PeersRecyclerViewAdapter(peerList);
        //Connect RV to adapter
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    //Just for testing
    private List<Peer> generateDummyPeers() {
        List<Peer> peerList = new ArrayList<>();
        for (int i = 0; i<10; i++){
            peerList.add(new Peer("User "+i, "Analyst", "Creative", "Entrepreneur",R.drawable.profile_icon));
        }
        return peerList;
    }
}