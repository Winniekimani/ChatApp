package com.winnie.chatapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.winnie.chatapp.Models.Message;
import com.winnie.chatapp.R;

import java.util.ArrayList;
import java.util.Objects;

public class MessagesAdapter extends RecyclerView.Adapter<MessagesAdapter.MessagesViewHolder>{
    ArrayList<Message>messageArrayList;
    Context context;

    public static final int MESSAGE_RECEIVED =  1;
    public  static final int MESSAGE_SENT = 2;

    public MessagesAdapter(ArrayList<Message> messageArrayList, Context context) {
        this.messageArrayList = messageArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public MessagesAdapter.MessagesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if (viewType == MESSAGE_SENT){
            view = LayoutInflater.from(context).inflate(R.layout.message_item,parent,false);
        }else {
            view = LayoutInflater.from(context).inflate(R.layout.message_item_recieved,parent,false);
        }

      return new MessagesViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull MessagesAdapter.MessagesViewHolder holder, int position) {

            Message message = messageArrayList.get(position);

            if (holder.getItemViewType() == MESSAGE_RECEIVED){
                holder.message1.setText(message.getMessage());
                
            }else {
                holder.message.setText(message.getMessage());
            }



    }

    @Override
    public int getItemCount() {
        return messageArrayList.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (messageArrayList.get(position).getSender_id()
                .equals(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid())){
            return MESSAGE_SENT;
        }else {
            return MESSAGE_RECEIVED;
        }
    }

    public static class MessagesViewHolder  extends RecyclerView.ViewHolder{

        //FOR SENT
        TextView message;

        //FOR RECEIVED
        TextView message1;

        public MessagesViewHolder(@NonNull View itemView) {
            super(itemView);

            //FOR SENT
            message = itemView.findViewById(R.id.message);

            //FOR RECEIVED
            message1 = itemView.findViewById(R.id.message1);

        }
    }
}