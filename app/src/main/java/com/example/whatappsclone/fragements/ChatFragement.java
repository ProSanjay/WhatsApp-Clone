package com.example.whatappsclone.fragements;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.whatappsclone.adapter.ProfileAdpater;
import com.example.whatappsclone.databinding.FragmentChatFragementBinding;
import com.example.whatappsclone.model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class ChatFragement extends Fragment {



    public ChatFragement() {
        // Required empty public constructor
    }
    FragmentChatFragementBinding binding;
    ArrayList<User>list=new ArrayList<>();
    FirebaseDatabase database;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding=FragmentChatFragementBinding.inflate(inflater, container, false);
        database=FirebaseDatabase.getInstance();
        ProfileAdpater adapter=new ProfileAdpater(list,getContext());
        binding.recyclerView.setAdapter(adapter);
        LinearLayoutManager maganer=new LinearLayoutManager(getContext());
        binding.recyclerView.setLayoutManager(maganer);
        database.getReference().child("Users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull   DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                    User user=dataSnapshot.getValue(User.class);
                    user.setUserId(dataSnapshot.getKey());
                    list.add(user);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return  binding.getRoot();

    }
}