package com.example.unipaths.Activities;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.unipaths.Adapter.TagsAdapter;
import com.example.unipaths.Models.Tag;
import com.example.unipaths.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class TagsFragment extends Fragment {

    private RecyclerView recyclerView;
    private TagsAdapter tagsAdapter;
    private List<Tag> tagList;

    EditText search_bar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tags, container, false);

        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        search_bar = view.findViewById(R.id.search_bar);

        tagList = new ArrayList<>(); // Retrieve tags from your database and add them to this list
        tagsAdapter = new TagsAdapter(getContext(), tagList, true);
        recyclerView.setAdapter(tagsAdapter);

        readTags();

        search_bar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                searchTags(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        return view;
    }

    private void searchTags(String s) {
        Log.d("SearchUsers", "Searching for: " + s);
        Query query = FirebaseDatabase.getInstance().getReference("Tags")
                .orderByChild("TagName")
                .startAt(s)
                .endAt(s + "\uf8ff");

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                tagList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Tag user = snapshot.getValue(Tag.class);
                    tagList.add(user);
                }

                tagsAdapter.notifyDataSetChanged();
                Log.d("SearchUsers", "Data loaded: " + tagList.size() + " users");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("SearchUsers", "Database error: " + error.getMessage());
            }
        });
    }

    private void readTags(){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Tags");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(search_bar.getText().toString().equals("")){
                    tagList.clear();
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                        Tag user = snapshot.getValue(Tag.class);
                        tagList.add(user);
                    }

                    tagsAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}