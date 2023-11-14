package com.example.unipaths.Activities;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.unipaths.R;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;


public class AddPost extends Fragment {

    Uri imageUri;
    String myUrl = "";
    StorageTask uploadTask;
    private static final int PICK_IMAGE_REQUEST = 1;
    private static final int RESULT_OK = -1;
    StorageReference storageReference;
    Button mButtonChooseImage;
    ImageView close, image_added;
    TextView post;
    EditText description;
    TagsInputEditText editText;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_post, container, false);

        close = view.findViewById(R.id.close);
        image_added = view.findViewById(R.id.image_added);
        post = view.findViewById(R.id.post);
        description = view.findViewById(R.id.description);
        mButtonChooseImage = view.findViewById(R.id.mButtonChooseImage);

        MaterialButton materialButton = view.findViewById(R.id.btn);
        editText = view.findViewById(R.id.tagsET);

        materialButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Tags added successfully!", Toast.LENGTH_SHORT).show();
            }
        });

        storageReference = FirebaseStorage.getInstance().getReference("posts");

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), DiscussionForum.class);
                startActivity(intent);
            }
        });

        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadImage();
            }
        });

        mButtonChooseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();
            }
        });

        return view;
    }

    private void openFileChooser(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    private String getFileExtension(Uri uri){
        ContentResolver contentResolver = getContext().getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    private void uploadImage(){
        ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Posting");
        progressDialog.show();

        if(imageUri != null){
            StorageReference filereference = storageReference.child(System.currentTimeMillis()
                    + "." + getFileExtension(imageUri));

            uploadTask = filereference.putFile(imageUri);
            uploadTask.continueWithTask(new Continuation() {
                @Override
                public Object then(@NonNull Task task) throws Exception {
                    if(!task.isSuccessful()){
                        throw task.getException();
                    }

                    return filereference.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if(task.isSuccessful()){
                        Uri downloadUri = task.getResult();
                        myUrl = downloadUri.toString();

                        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Posts");

                        String postid = reference.push().getKey();

                        HashMap<String, Object> hashMap = new HashMap<>();
                        hashMap.put("postid", postid);
                        hashMap.put("postimage", myUrl);
                        hashMap.put("description", description.getText().toString());
                        hashMap.put("publisher", FirebaseAuth.getInstance().getCurrentUser().getUid());
                        String[] tagsArray = editText.getText().toString().split("\\s+");
                        List<String> tagsList = Arrays.asList(tagsArray);
                        hashMap.put("tags", tagsList);

                        reference.child(postid).setValue(hashMap);
                        for (String tag : tagsList) {
                            updateOrAddTag(tag, postid);
                        }
                        progressDialog.dismiss();

                        Intent intent = new Intent(getActivity(), DiscussionForum.class);
                        startActivity(intent);
                    }else{
                        Toast.makeText(getContext(), "Failed!", Toast.LENGTH_SHORT).show();
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getContext(), ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }else {
            Toast.makeText(getContext(), "No image selected", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null){
            imageUri = data.getData();

            image_added.setImageURI(imageUri);
        }else{
            Toast.makeText(getContext(), "Something gone wrong", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getActivity(), DiscussionForum.class);
            startActivity(intent);
        }
    }

    private void updateOrAddTag(String tagName, String postid) {
        DatabaseReference tagsReference = FirebaseDatabase.getInstance().getReference("Tags");

        // Check if the tag with the same name already exists
        tagsReference.orderByChild("TagName").equalTo(tagName).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // Update the existing tag
                    DataSnapshot tagSnapshot = dataSnapshot.getChildren().iterator().next();
                    String existingTagId = tagSnapshot.getKey();
                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Posts");
                    tagsReference.child(existingTagId).child("Posts").child(postid).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                reference.child(postid).child("tagId").child(existingTagId).setValue(existingTagId);
                            } else {
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            // Handle the error
                        }
                    });
                    updateExistingTag(existingTagId, postid);
                } else {
                    // Create a new tag
                    createNewTag(tagName, postid);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle the error
            }
        });
    }

    private void updateExistingTag(String tagId, String postid) {
        DatabaseReference tagReference = FirebaseDatabase.getInstance().getReference().child("Tags").child(tagId);

        // Add post
        tagReference.child("Posts").child(postid).setValue(true);

        // Increment postCount
        tagReference.child("postCount").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                long currentPostCount = 0;
                if (dataSnapshot.exists()) {
                    currentPostCount = (long) dataSnapshot.getValue();
                }
                tagReference.child("postCount").setValue(currentPostCount + 1);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle the error
            }
        });
    }

    private void createNewTag(String tagName, String postid) {
        DatabaseReference tagsReference = FirebaseDatabase.getInstance().getReference("Tags");
        String tagId = tagsReference.push().getKey(); // Generate a random tag ID


        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Posts");

        DatabaseReference tagReference = tagsReference.child(tagId);
        tagReference.child("TagName").setValue(tagName);
        tagReference.child("TagId").setValue(tagId);
        tagsReference.child(tagId).child("Posts").child(postid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // If the post exists under "Tags" node, update the post with the tagId
                    reference.child(postid).child("tagId").child(tagId).setValue(tagId);
                } else {
                    // Handle the case where the post doesn't exist under "Tags" node
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle the error
            }
        });
        tagReference.child("postCount").setValue(1); // Initialize postCount to 1 for the new tag

        // Add follower
        tagReference.child("Followers").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(true);

        // Add post
        tagReference.child("Posts").child(postid).setValue(true);
    }

}