package com.example.whatappsclone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.whatappsclone.adapter.UserChatAdapter;
import com.example.whatappsclone.databinding.ActivityUserChatBinding;
import com.example.whatappsclone.fragements.ChatFragement;
import com.example.whatappsclone.model.Chat;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Date;

public class UserChat extends AppCompatActivity {
 ActivityUserChatBinding binding;
 FirebaseAuth auth;
 FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityUserChatBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        auth=FirebaseAuth.getInstance();
        database=FirebaseDatabase.getInstance();
        getSupportActionBar().hide();
       String senderId= auth.getUid();
       String receiverID=getIntent().getStringExtra("receiverId");
       String profilePic=getIntent().getStringExtra("profilePic");
       String userName=getIntent().getStringExtra("profilename");
       binding.profileNAme.setText(userName);
        Picasso.get().load(profilePic).placeholder(R.drawable.profile).into(binding.profilepic);
        binding.backarrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UserChat.this, MainActivity.class));
                finish();
            }
        });
        final String senderRoom=senderId+receiverID;
        final String receiverRoom=receiverID+senderId;
        ArrayList<Chat> messageModels=new ArrayList<>();
        UserChatAdapter adapter=new UserChatAdapter(messageModels, this);
        binding.chaatview.setAdapter(adapter);
        LinearLayoutManager manager=new LinearLayoutManager(this);
        binding.chaatview.setLayoutManager(manager);

        binding.send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message=binding.chatText.getText().toString();
                final Chat chat=new Chat(message,senderId);
                chat.setTime(new Date().getTime());
                binding.chatText.setText("");
                database.getReference().child("UserChat").child(senderRoom)
                        .push() //push method generates a new child with unique key  to the specified
                        //firebase reference
                        .setValue(chat).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                   database.getReference().child("UserChat").child(receiverRoom).push().setValue(chat).addOnSuccessListener(new OnSuccessListener<Void>() {
                       @Override
                       public void onSuccess(Void unused) {

                       }
                   });
                    }
                });

            }
        });

        database.getReference().child("UserChat").child(senderRoom).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull  DataSnapshot snapshot) {
                messageModels.clear();
                for(DataSnapshot snapshot1:snapshot.getChildren()){
                    Chat chat1=snapshot1.getValue(Chat.class);
                    messageModels.add(chat1);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull  DatabaseError error) {

            }
        });

    }
}