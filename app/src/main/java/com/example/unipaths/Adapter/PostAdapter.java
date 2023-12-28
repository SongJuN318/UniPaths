package com.example.unipaths.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.unipaths.Activities.CommentActivity;
import com.example.unipaths.Activities.DiscussionFragment;
import com.example.unipaths.Activities.FollowerActivity;
import com.example.unipaths.Activities.PostDetailFragment;
import com.example.unipaths.Activities.ProfileFragment;
import com.example.unipaths.Models.Post;
import com.example.unipaths.R;
import com.example.unipaths.Models.UserHelperClass;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder>{

    public Context mContext;
    public List<Post> mPost;

    private FirebaseUser firebaseUser;

    public PostAdapter(Context mContext, List<Post> mPost) {
        this.mContext = mContext;
        this.mPost = mPost;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.post_item,viewGroup, false);
        return new PostAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        Post post = mPost.get(i);

        Log.d("PostAdapter", "Post Image URL: " + post.getpostimage());
        Glide.with(mContext).load(post.getpostimage())
                .apply(new RequestOptions().placeholder(R.drawable.placeholder))
                .into(viewHolder.post_image);

        if(post.getDescription().equals("")){
            viewHolder.description.setVisibility(View.GONE);
        }else{
            viewHolder.description.setVisibility(View.VISIBLE);
            viewHolder.description.setText(post.getDescription());
        }

        publisherInfo(viewHolder.image_profile, viewHolder.username, viewHolder.publisher, post.getPublisher());
        isLiked(post.getPostid(), viewHolder.like);
        nrLikes(viewHolder.likes, post.getPostid());
        getComments(post.getPostid(), viewHolder.comments);
        isSaved(post.getPostid(), viewHolder.save);
        viewHolder.tags.setText(TextUtils.join(", ", post.getTags()));

        List<String> tags = post.getTags();

        if (tags != null && !tags.isEmpty()) {
            StringBuilder tagsText = new StringBuilder();
            for (String tag : tags) {
                tagsText.append("#").append(tag).append(" ");
            }

            viewHolder.tags.setText(tagsText.toString().trim());
        } else {
            viewHolder.tags.setText("");
        }

        viewHolder.image_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = mContext.getSharedPreferences("PREPS", Context.MODE_PRIVATE).edit();
                editor.putString("profileid", post.getPublisher());
                editor.apply();

                ((FragmentActivity)mContext).getSupportFragmentManager().beginTransaction().replace(R.id.fram_container,
                new ProfileFragment()).commit();

            }
        });

        viewHolder.username.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = mContext.getSharedPreferences("PREPS", Context.MODE_PRIVATE).edit();
                editor.putString("profileid", post.getPublisher());
                editor.apply();

                ((FragmentActivity)mContext).getSupportFragmentManager().beginTransaction().replace(R.id.fram_container,
                        new ProfileFragment()).commit();

            }
        });

        viewHolder.publisher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = mContext.getSharedPreferences("PREPS", Context.MODE_PRIVATE).edit();
                editor.putString("profileid", post.getPublisher());
                editor.apply();

                ((FragmentActivity)mContext).getSupportFragmentManager().beginTransaction().replace(R.id.fram_container,
                        new ProfileFragment()).commit();

            }
        });

        viewHolder.post_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = mContext.getSharedPreferences("PREPS", Context.MODE_PRIVATE).edit();
                editor.putString("postid", post.getPostid());
                editor.apply();

                ((FragmentActivity)mContext).getSupportFragmentManager().beginTransaction().replace(R.id.fram_container,
                        new PostDetailFragment()).commit();

            }
        });

        viewHolder.save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(viewHolder.save.getTag().equals("save")){
                    FirebaseDatabase.getInstance().getReference().child("Saves").child(firebaseUser.getUid())
                            .child(post.getPostid()).setValue(true);
                }else{
                    FirebaseDatabase.getInstance().getReference().child("Saves").child(firebaseUser.getUid())
                            .child(post.getPostid()).removeValue();
                }
            }
        });

        viewHolder.like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(viewHolder.like.getTag().equals("like")){
                    FirebaseDatabase.getInstance().getReference().child("Likes").child(post.getPostid())
                            .child(firebaseUser.getUid()).setValue(true);
                }else{
                    FirebaseDatabase.getInstance().getReference().child("Likes").child(post.getPostid())
                            .child(firebaseUser.getUid()).removeValue();
                }
            }
        });

        viewHolder.comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, CommentActivity.class);
                intent.putExtra("postid", post.getPostid());
                intent.putExtra("publisherid", post.getPublisher());
                mContext.startActivity(intent);
            }
        });

        viewHolder.comments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, CommentActivity.class);
                intent.putExtra("postid", post.getPostid());
                intent.putExtra("publisherid", post.getPublisher());
                mContext.startActivity(intent);
            }
        });

        viewHolder.likes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, FollowerActivity.class);
                intent.putExtra("id", post.getPostid());
                intent.putExtra("title", "likes");
                mContext.startActivity(intent);
            }
        });

        viewHolder.more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(mContext, v);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        if (menuItem.getItemId() == R.id.edit) {
                            editPost(post.getPostid());
                            return true;
                        } else if (menuItem.getItemId() == R.id.delete) {
                            FirebaseDatabase.getInstance().getReference("Posts")
                                    .child(post.getPostid()).removeValue()
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                List<String> tagsList = post.getTags();

                                                // Update each tag's postCount
                                                for (String tagId : tagsList) {
                                                    decrementTagPostCount(tagId);
                                                }

                                                Toast.makeText(mContext, "Deleted!", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                            return true;
                        } else if (menuItem.getItemId() == R.id.report) {
                            Toast.makeText(mContext, "Report clicked!", Toast.LENGTH_SHORT).show();
                            return true;
                        } else {
                            return false;
                        }
                    }
                });
                popupMenu.inflate(R.menu.post_menu);
                if(!post.getPublisher().equals(firebaseUser.getUid())){
                    popupMenu.getMenu().findItem(R.id.edit).setVisible(false);
                    popupMenu.getMenu().findItem(R.id.delete).setVisible(false);
                }
                popupMenu.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mPost.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public ImageView image_profile, post_image, like, comment, save, more;
        public TextView username, likes, publisher, description, comments, tags;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            image_profile = itemView.findViewById(R.id.image_profile);
            post_image = itemView.findViewById(R.id.post_image);
            like = itemView.findViewById(R.id.like);
            comment = itemView.findViewById(R.id.comment);
            save = itemView.findViewById(R.id.save);
            username = itemView.findViewById(R.id.username);
            publisher = itemView.findViewById(R.id.publisher);
            description = itemView.findViewById(R.id.description);
            comments = itemView.findViewById(R.id.comments);
            likes = itemView.findViewById(R.id.likes);
            more = itemView.findViewById(R.id.more);
            tags = itemView.findViewById(R.id.tags_tv);
        }
    }

    private void getComments(String postid, TextView comments){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Comments").child(postid);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                comments.setText("View All " + snapshot.getChildrenCount() + " Comments");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void isLiked(String postid, ImageView imageView){


        DatabaseReference reference = FirebaseDatabase.getInstance().getReference()
                .child("Likes")
                .child(postid);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.child(firebaseUser.getUid()).exists()){
                    imageView.setImageResource(R.drawable.liked_icon);
                    imageView.setTag("liked");
                }else{
                    imageView.setImageResource(R.drawable.like_icon);
                    imageView.setTag("like");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void nrLikes(TextView likes, String postid){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Likes")
                .child(postid);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                likes.setText(snapshot.getChildrenCount() + "likes");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void publisherInfo(ImageView image_profile, TextView username, TextView publisher, String userid){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users").child(userid);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                UserHelperClass user = snapshot.getValue(UserHelperClass.class);
                Glide.with(mContext).load(user.getImageurl()).into(image_profile);
                username.setText(user.getName());
                publisher.setText(user.getBio());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void isSaved(String postid, ImageView imageView){
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Saves")
                .child(firebaseUser.getUid());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.child(postid).exists()){
                    imageView.setImageResource(R.drawable.saved_icon);
                    imageView.setTag("saved");
                }else{
                    imageView.setImageResource(R.drawable.save_icon);
                    imageView.setTag("save");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void editPost(String postid) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);
        alertDialog.setTitle("Edit Post");

        EditText editText = new EditText(mContext);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
        );
        editText.setLayoutParams(lp);
        alertDialog.setView(editText);

        // Fetch the current text of the post and set it in the EditText
        getText(postid, editText);

        alertDialog.setPositiveButton("Edit",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Get the new description from the EditText
                        String newDescription = editText.getText().toString();

                        // Update the post's description in the database
                        HashMap<String, Object> hashMap = new HashMap<>();
                        hashMap.put("description", newDescription);

                        FirebaseDatabase.getInstance().getReference("Posts")
                                .child(postid).updateChildren(hashMap);
                    }
                });

        alertDialog.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        dialogInterface.dismiss();
                    }
                });

        alertDialog.show();
    }

    private void getText(String postid, EditText editText) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Posts")
                .child(postid);

        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    Post post = dataSnapshot.getValue(Post.class);
                    if (post != null) {
                        String currentDescription = post.getDescription();
                        editText.setText(currentDescription);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle the error
            }
        });
    }

    private void decrementTagPostCount(String tagId) {
        DatabaseReference tagReference = FirebaseDatabase.getInstance().getReference().child("Tags").child(tagId);
        tagReference.child("postCount").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                long currentPostCount = 0;
                if (dataSnapshot.exists()) {
                    currentPostCount = dataSnapshot.getValue(Long.class);
                }

                // Ensure postCount doesn't go below zero
                long updatedPostCount = Math.max(0, currentPostCount - 1);

                if (updatedPostCount == 0) {
                    // If post count becomes zero, delete the tag
                    deleteTag(tagId);
                } else {
                    tagReference.child("postCount").setValue(updatedPostCount)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        // Tag postCount updated successfully
                                    } else {
                                        // Handle the error
                                    }
                                }
                            });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle the error
            }
        });
    }

    private void deleteTag(String tagId) {
        FirebaseDatabase.getInstance().getReference("Tags").child(tagId).removeValue()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            // Tag deleted successfully
                        } else {
                            // Handle the error
                        }
                    }
                });
    }
}
