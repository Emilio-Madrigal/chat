package com.example.chat.apatador;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chat.R;
import com.example.chat.model.ChatRoomModel;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class ChatRoomAdapterFirestore extends FirestoreRecyclerAdapter<ChatRoomModel, ChatRoomAdapterFirestore.ChatRoomViewHolder> {

    Context context;

    public ChatRoomAdapterFirestore(@NonNull FirestoreRecyclerOptions<ChatRoomModel> options, Context context) {
        super(options);
        this.context = context;
    }

    @Override
    protected void onBindViewHolder(@NonNull ChatRoomViewHolder holder, int position, @NonNull ChatRoomModel model) {
        holder.username.setText(model.getNombre());
        holder.fecha.setText(model.getUltimoMensaje());  // Muestra el último mensaje como fecha (puedes formatear si es Timestamp)

    }

    @NonNull
    @Override
    public ChatRoomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_chat_room, parent, false);
        return new ChatRoomViewHolder(view);
    }

    static class ChatRoomViewHolder extends RecyclerView.ViewHolder {
        TextView username;
        TextView fecha;
        ImageView foto;

        public ChatRoomViewHolder(@NonNull View itemView) {
            super(itemView);
            username = itemView.findViewById(R.id.USERname);
            fecha = itemView.findViewById(R.id.lastmessage);
            foto = itemView.findViewById(R.id.fotoDePerfil);
        }
    }
}
