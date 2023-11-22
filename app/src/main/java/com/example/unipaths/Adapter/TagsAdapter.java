package com.example.unipaths.Adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.unipaths.Activities.CommentActivity;
import com.example.unipaths.Activities.DiscussionForum;
import com.example.unipaths.Activities.ProfileFragment;
import com.example.unipaths.Activities.RegisterActivity;
import com.example.unipaths.Activities.SignUpPage;
import com.example.unipaths.Activities.Tag_Display_Fragment;
import com.example.unipaths.Activities.TagsFragment;
import com.example.unipaths.Models.Tag;
import com.example.unipaths.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class TagsAdapter extends RecyclerView.Adapter<TagsAdapter.ViewHolder> {

    private Context mContext;
    private List<Tag> mTags;
    private FirebaseUser firebaseUser;
    private boolean isfragment;

    public TagsAdapter(Context mContext, List<Tag> mTags, boolean isfragment) {
        this.mContext = mContext;
        this.mTags = mTags;
        this.isfragment = isfragment;
    }

    @NonNull
    @Override
    public TagsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.tag_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TagsAdapter.ViewHolder viewHolder, int i) {
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        Tag tagItem = mTags.get(i);

        viewHolder.btn_follow.setVisibility(View.VISIBLE);
        viewHolder.tagName.setText(tagItem.getTagName());
        viewHolder.postsCount.setText(tagItem.getPostCount() + " Posts");
        isFollowing(tagItem.getTagId(), viewHolder.btn_follow);
        fetchPostsCount(tagItem.getTagId(), viewHolder.postsCount);

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isfragment) {
                    SharedPreferences.Editor editor = mContext.getSharedPreferences("PREPS", Context.MODE_PRIVATE).edit();
                    editor.putString("tagsid", tagItem.getTagId());
                    editor.apply();

                    ((FragmentActivity) mContext).getSupportFragmentManager().beginTransaction().replace(R.id.fram_container, new Tag_Display_Fragment()).commit();
                }else{
                    Intent intent = new Intent(mContext, ProfileFragment.class);
                    intent.putExtra("tagsid", tagItem.getTagId());
                    mContext.startActivity(intent);
                }
                ((FragmentActivity) mContext).getSupportFragmentManager().beginTransaction().replace(R.id.fram_container, new Tag_Display_Fragment()).commit();
            }
        });

        viewHolder.btn_follow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (viewHolder.btn_follow.getText().toString().equals("following")) {
                    viewHolder.btn_follow.setText("follow");
                    FirebaseDatabase.getInstance().getReference()
                            .child("Tags")
                            .child(tagItem.getTagId())
                            .child("Followers")
                            .child(firebaseUser.getUid())
                            .removeValue();
                } else {
                    viewHolder.btn_follow.setText("following");
                    FirebaseDatabase.getInstance().getReference()
                            .child("Tags")
                            .child(tagItem.getTagId())
                            .child("Followers")
                            .child(firebaseUser.getUid())
                            .setValue(true);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mTags.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView tagName;
        public TextView postsCount, tags_tv;
        public Button btn_follow;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tagName = itemView.findViewById(R.id.tag_name);
            postsCount = itemView.findViewById(R.id.posts_count);
            btn_follow = itemView.findViewById(R.id.btn_follow);
            tags_tv = itemView.findViewById(R.id.tags_tv);
        }
    }

    private void isFollowing(String tagId, Button button) {
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        if (firebaseUser != null) {
            DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Tags").child(tagId).child("Followers");

            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.child(firebaseUser.getUid()).exists()) {
                        button.setText("following");
                    } else {
                        button.setText("follow");
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    }

    private void fetchPostsCount(String tagId, TextView postsCountTextView) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Tags").child(tagId).child("Posts");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    long postsCount = dataSnapshot.getChildrenCount();
                    postsCountTextView.setText(postsCount + " Posts");
                } else {
                    postsCountTextView.setText("0 Posts");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle the error
            }
        });
    }
}