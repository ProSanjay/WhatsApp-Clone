package com.example.whatappsclone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;

import com.example.whatappsclone.adapter.GroupChatAdapter;
import com.example.whatappsclone.databinding.ActivityGroupChatBinding;
import com.example.whatappsclone.model.GroupChatModel;
import com.example.whatappsclone.model.User;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;

public class GroupChat extends AppCompatActivity {
   ActivityGroupChatBinding binding;
   FirebaseDatabase database;
   FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityGroupChatBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
       database=FirebaseDatabase.getInstance();
       auth=FirebaseAuth.getInstance();
        String senderId= auth.getUid();
        getSupportActionBar().hide();
        ArrayList<GroupChatModel> groupChatModels=new ArrayList<>();
        GroupChatAdapter adapter=new GroupChatAdapter(groupChatModels,this);
        binding.groupchatView.setAdapter(adapter);
        LinearLayoutManager manager=new LinearLayoutManager(this);
        binding.groupchatView.setLayoutManager(manager);
        binding.backarrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(GroupChat.this,MainActivity.class));
                finish();
            }
        });


        binding.Gsend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int i=0;
                int finalI = i;
                final String[] usrName = new String[finalI+1];

                database.getReference().child("Users").child(senderId).get().addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
             @Override
             public void onSuccess(DataSnapshot dataSnapshot) {
              usrName[finalI] =String.valueOf(dataSnapshot.child("userName").getValue());
                binding.profileNAme.setText(usrName[finalI]);

             }
         });

        i=i+1;
               final String message=binding.rchatText.getText().toString();

                GroupChatModel model=new GroupChatModel(senderId,message, usrName);
                model.setTime(new Date().getTime());
                binding.rchatText.setText("");
                database.getReference().child("GroupChat").push().setValue(model)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {

                            }
                        });
            }
        });
        database.getReference().child("GroupChat").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull  DataSnapshot snapshot) {
                groupChatModels.clear();
                for(DataSnapshot snapshot1:snapshot.getChildren()){
                    GroupChatModel groupChatModel1=snapshot1.getValue(GroupChatModel.class);
                    groupChatModels.add(groupChatModel1);

                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull  DatabaseError error) {

            }
        });
    }
}