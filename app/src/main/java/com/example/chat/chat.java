package com.example.chat;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.chat.apatador.ChatRoomAdapterFirestore;
import com.example.chat.model.ChatRoomModel;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class chat extends Fragment {
    private RecyclerView recyclerView;
    private ChatRoomAdapterFirestore adapter;
    private FirebaseFirestore db;
    public chat() {}
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chat, container, false);
        recyclerView = view.findViewById(R.id.recyclerChatRooms);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        db = FirebaseFirestore.getInstance();
        Query query = db.collection("chatrooms").orderBy("timestamp", Query.Direction.DESCENDING);
        FirestoreRecyclerOptions<ChatRoomModel> options = new FirestoreRecyclerOptions.Builder<ChatRoomModel>().setQuery(query, ChatRoomModel.class).setLifecycleOwner(this).build();
        adapter = new ChatRoomAdapterFirestore(options, getContext());
        recyclerView.setAdapter(adapter);
        return view;
    }
}