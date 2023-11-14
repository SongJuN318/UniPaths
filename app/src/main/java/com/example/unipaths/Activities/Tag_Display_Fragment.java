package com.example.unipaths.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.unipaths.Adapter.MyFotoAdapter;
import com.example.unipaths.Adapter.TagsAdapter;
import com.example.unipaths.Models.Post;
import com.example.unipaths.Models.Tag;
import com.example.unipaths.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Tag_Display_Fragment extends Fragment {

    TextView tagName, postCount;
    Button btn_follow;

    private List<Post> postList;
    private List<String> myTags;
    RecyclerView recyclerView;
    MyFotoAdapter myFotoAdapter;
    FirebaseUser firebaseUser;
    String tagsid;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tag__display_, container, false);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        SharedPreferences prefs = getContext().getSharedPreferences("PREPS", Context.MODE_PRIVATE);
        tagsid = prefs.getString("tagsid", "none");

        tagName = view.findViewById(R.id.tagName);
        postCount = view.findViewById(R.id.postCount);
        btn_follow = view.findViewById(R.id.btn_follow);
        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new GridLayoutManager(getContext(), 3);
        recyclerView.setLayoutManager(linearLayoutManager);
        postList  = new ArrayList<>();
        myFotoAdapter  = new MyFotoAdapter(getContext(), postList);
        recyclerView.setAdapter(myFotoAdapter);
        recyclerView.setVisibility(View.VISIBLE);

        fetchTagInfo();
        checkFollow();
        fetchPostsForTag();
        updatePostsCount();

        btn_follow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String btn = btn_follow.getText().toString();

                if(btn.equals("follow")){
                    FirebaseDatabase.getInstance().getReference().child("Tags").child(tagsid).child("Followers").child(firebaseUser.getUid()).setValue(true);
                }else if(btn.equals("following")){
                    FirebaseDatabase.getInstance().getReference().child("Tags").child(tagsid).child("Followers").child(firebaseUser.getUid()).removeValue();
                }
            }
        });

        return view;
    }

    private void checkFollow(){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference()
                .child("Tags").child(tagsid).child("Followers");

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.child(firebaseUser.getUid()).exists()){
                    btn_follow.setText("following");
                }else{
                    btn_follow.setText("follow");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void fetchTagInfo() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Tags").child(tagsid);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (getContext() == null) {
                    return;
                }

                Tag tag = dataSnapshot.getValue(Tag.class);
                if (tag != null) {
                    if (tagName != null) {
                        tagName.setText(tag.getTagName());
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle the error
            }
        });
    }

    private void fetchPostsForTag() {
        myTags = new ArrayList<>();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Tags").child(tagsid).child("Posts");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    myTags.add(snapshot.getKey());
                }

                fetchPosts();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle the error
            }
        });
    }

    private void fetchPosts() {
        DatabaseReference postsReference = FirebaseDatabase.getInstance().getReference("Posts");

        postsReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                postList.clear();
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Post post = snapshot.getValue(Post.class);

                    for(String id :myTags){
                        if(post.getPostid().equals(id)){
                            postList.add(post);
                        }
                    }
                }
                myFotoAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle the error
            }
        });
    }

    private void updatePostsCount() {
        DatabaseReference tagsReference = FirebaseDatabase.getInstance().getReference("Tags").child(tagsid);
        tagsReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    Tag tag = dataSnapshot.getValue(Tag.class);
                    if (tag != null) {
                        int count = tag.getPostCount();
                        postCount.setText(String.valueOf(count));
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle the error
            }
        });
    }
}