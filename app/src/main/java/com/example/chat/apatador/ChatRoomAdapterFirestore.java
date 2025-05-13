package com.example.chat.apatador;
import android.annotation.SuppressLint;
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
import com.example.chat.global;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
public class ChatRoomAdapterFirestore extends FirestoreRecyclerAdapter<ChatRoomModel, ChatRoomAdapterFirestore.ChatRoomViewHolder> {
    Context context;
    public ChatRoomAdapterFirestore(@NonNull FirestoreRecyclerOptions<ChatRoomModel> options, Context context) {
        super(options);
        this.context = context;
    }
    @SuppressLint("SetTextI18n")
    @Override
    protected void onBindViewHolder(@NonNull ChatRoomViewHolder holder, int position, @NonNull ChatRoomModel model) {
        String currentUid = global.getInstance().getUid();
        String otherUserUid = null;
        for (String uid : model.getUsuarios()) {
            if (!uid.equals(currentUid)) {
                otherUserUid = uid;
                break;
            }
        }
        if (otherUserUid != null) {
            FirebaseFirestore.getInstance()
                    .collection("users")
                    .document(otherUserUid)
                    .get()
                    .addOnSuccessListener(documentSnapshot -> {
                        if (documentSnapshot.exists()) {
                            String nombre = documentSnapshot.getString("username");
                            holder.username.setText(nombre);
                        } else {
                            holder.username.setText("Usuario desconocido");
                        }
                    })
                    .addOnFailureListener(e -> holder.username.setText("Error al cargar"));
        } else {
            holder.username.setText("Sin usuarios");
        }
        holder.fecha.setText(model.getUltimoMensaje());
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