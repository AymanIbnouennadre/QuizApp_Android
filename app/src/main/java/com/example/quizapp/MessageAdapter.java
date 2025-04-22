package com.example.quizapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.quizapp.model.Message;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_USER = 0;
    private static final int TYPE_BOT = 1;

    private final List<Message> messageList;
    private final Context context;

    public MessageAdapter(List<Message> messageList, Context context) {
        this.messageList = messageList;
        this.context = context;
    }

    @Override
    public int getItemViewType(int position) {
        return messageList.get(position).getSender().equals("user") ? TYPE_USER : TYPE_BOT;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if (viewType == TYPE_USER) {
            view = LayoutInflater.from(context).inflate(R.layout.item_message_user, parent, false);
            return new UserViewHolder(view);
        } else {
            view = LayoutInflater.from(context).inflate(R.layout.item_message_bot, parent, false);
            return new BotViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Message message = messageList.get(position);
        if (holder instanceof UserViewHolder) {
            ((UserViewHolder) holder).userText.setText(message.getContent()); // ✅ Texte entré par user
        } else if (holder instanceof BotViewHolder) {
            ((BotViewHolder) holder).botText.setText(message.getContent()); // ✅ Réponse API
        }
    }


    @Override
    public int getItemCount() {
        return messageList.size();
    }

    static class UserViewHolder extends RecyclerView.ViewHolder {
        TextView userText;
        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            userText = itemView.findViewById(R.id.tvMessageUser);
        }
    }

    static class BotViewHolder extends RecyclerView.ViewHolder {
        TextView botText;
        public BotViewHolder(@NonNull View itemView) {
            super(itemView);
            botText = itemView.findViewById(R.id.tvMessageBot);
            ImageView botAvatar = itemView.findViewById(R.id.imgBotAvatar);
            botAvatar.setImageResource(R.drawable.ayman); // ← ✅ avatar personnalisé
        }
    }
}
