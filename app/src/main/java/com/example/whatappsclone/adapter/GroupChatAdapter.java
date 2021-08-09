package com.example.whatappsclone.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.whatappsclone.R;
import com.example.whatappsclone.model.GroupChatModel;
import com.example.whatappsclone.model.User;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class GroupChatAdapter extends RecyclerView.Adapter {
 ArrayList<GroupChatModel> groupChats=new ArrayList<>();
 Context context;
int GROUP_SENDER=1;
int GROUP_RECEIVER=2;
    public GroupChatAdapter(ArrayList<GroupChatModel> groupChats, Context context) {
        this.groupChats = groupChats;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {
        if(viewType==GROUP_SENDER){
            View view= LayoutInflater.from(context).inflate(R.layout.senderchat,parent,false);
            return  new GroupSenderViewHolder(view);
        }else{
            View view=LayoutInflater.from(context).inflate(R.layout.groupreceiverchat,parent,false);
            return new GroupReceiverViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
    GroupChatModel groupChatModel=groupChats.get(position);

    if(holder.getClass()==GroupSenderViewHolder.class){
        ((GroupSenderViewHolder) holder).message.setText(groupChatModel.getMessage());
    }else {
        ((GroupReceiverViewHolder)holder).RgroupMessage.setText(groupChatModel.getMessage());
        ((GroupReceiverViewHolder)holder).RgroupUserName.setText(groupChatModel.getUsrName());


    }
    }

    @Override
    public int getItemCount() {
        return groupChats.size();
    }

    @Override
    public int getItemViewType(int position) {
        if(groupChats.get(position).getUid().equals(FirebaseAuth.getInstance().getUid())){
            return GROUP_SENDER;
        }else{
            return GROUP_RECEIVER;
        }
    }

    public class GroupSenderViewHolder extends RecyclerView.ViewHolder{
       TextView message,time;
        public GroupSenderViewHolder(@NonNull View itemView) {
            super(itemView);
            message=itemView.findViewById(R.id.senderchat);
            time=itemView.findViewById(R.id.sendertime);
        }
    }
    public class GroupReceiverViewHolder extends RecyclerView.ViewHolder{
       TextView RgroupMessage,RgroupTime,RgroupUserName;
        public GroupReceiverViewHolder(@NonNull  View itemView) {
            super(itemView);
            RgroupMessage=itemView.findViewById(R.id.groupreciverchat);
            RgroupTime=itemView.findViewById(R.id.groupreceivertime);
            RgroupUserName=itemView.findViewById(R.id.receivername);
        }
    }
}
