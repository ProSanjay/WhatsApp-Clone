package com.example.whatappsclone.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.whatappsclone.R;
import com.example.whatappsclone.model.Chat;
import com.google.firebase.auth.FirebaseAuth;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class UserChatAdapter extends RecyclerView.Adapter {
    ArrayList<Chat> userMessage=new ArrayList<>();
    Context context;
   FirebaseAuth auth;
    public UserChatAdapter(ArrayList<Chat> userMessage, Context context) {
        this.userMessage = userMessage;
        this.context = context;
    }
   int SENDER_ITEM_VIEWTYPE=1;
    int RECEIVER_ITEM_VIEWTYPW=2;
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull   ViewGroup parent, int viewType) {
        if(viewType==SENDER_ITEM_VIEWTYPE){
            View view= LayoutInflater.from(context).inflate(R.layout.senderchat,parent,false);
            return new SenderViewHolder(view);
        } else{
            View view= LayoutInflater.from(context).inflate(R.layout.receiverchat,parent,false);
            return new RecieverViewHolder(view);
        }

    }

    @Override
    public void onBindViewHolder(@NonNull   RecyclerView.ViewHolder holder, int position) {
      Chat chat=userMessage.get(position);
      if(holder.getClass()==SenderViewHolder.class){
          ((SenderViewHolder)holder).message.setText(chat.getMessage());

      }else{
          ((RecieverViewHolder)holder).receiverMesssage.setText(chat.getMessage());

      }
    }

    @Override
    public int getItemCount() {
     return   userMessage.size();
    }

    @Override
    public int getItemViewType(int position) {
        if(userMessage.get(position).getUid().equals(FirebaseAuth.getInstance().getUid())){
            return  SENDER_ITEM_VIEWTYPE;
        }else{
            return RECEIVER_ITEM_VIEWTYPW;
        }

    }

    public class SenderViewHolder extends RecyclerView.ViewHolder{
   TextView message,time;
        public SenderViewHolder(@NonNull  View itemView) {
            super(itemView);
            message=itemView.findViewById(R.id.senderchat);
            time=itemView.findViewById(R.id.sendertime);
        }
    }
    public class RecieverViewHolder extends RecyclerView.ViewHolder{
      TextView receiverMesssage,receiverTime;
        public RecieverViewHolder(@NonNull  View itemView) {
            super(itemView);
            receiverMesssage=itemView.findViewById(R.id.reciverchat);
            receiverTime=itemView.findViewById(R.id.receivertime);

        }
    }
}
