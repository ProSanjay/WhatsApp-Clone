package com.example.whatappsclone.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.whatappsclone.R;
import com.example.whatappsclone.UserChat;
import com.example.whatappsclone.model.User;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ProfileAdpater extends RecyclerView.Adapter<ProfileAdpater.viewHolder> {
ArrayList<User> list;
Context context;

    public ProfileAdpater(ArrayList<User> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public viewHolder onCreateViewHolder(@NonNull   ViewGroup parent, int viewType) {
View view= LayoutInflater.from(context).inflate(R.layout.userchatsample,parent,false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull   ProfileAdpater.viewHolder holder, int position) {
        User user=list.get(position);
        Picasso.get().load(user.getProfilePic()).placeholder(R.drawable.profile).into(holder.imageview);
        holder.textView1.setText(user.getUserName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, UserChat.class);
                intent.putExtra("profilePic",user.getProfilePic());
                intent.putExtra("profilename",user.getUserName());
                intent.putExtra("receiverId",user.getUserId());
                context.startActivity(intent);

            }
        });
//        holder.textView2.setText(user.getLastMassage());


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public  class  viewHolder extends RecyclerView.ViewHolder{
   ImageView imageview;
   TextView textView1,textView2;
        public viewHolder(@NonNull  View itemView) {
            super(itemView);
            imageview=itemView.findViewById(R.id.profilepic);
            textView1=itemView.findViewById(R.id.userName);
            textView2=itemView.findViewById(R.id.lastmessage);

        }
    }
}
