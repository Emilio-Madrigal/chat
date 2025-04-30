package com.example.chat;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class chat extends Fragment {

    private ListView listView;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> userList;
    private FirebaseFirestore db;

    public chat() {

    }

    public static chat newInstance(String param1, String param2) {
        chat fragment = new chat();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //listView = findViewById(R.id.usuarios); // Asegúrate de que el ID coincida
        userList = new ArrayList<>();
        //adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, userList);
        listView.setAdapter(adapter);

        db = FirebaseFirestore.getInstance();

        cargarUsuarios();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_chat, container, false);
    }

    private void cargarUsuarios() {
        CollectionReference usuariosRef = db.collection("usuarios");

        usuariosRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                QuerySnapshot documentos = task.getResult();
                userList.clear(); // Por si recargas datos

                for (DocumentSnapshot doc : documentos.getDocuments()) {
                    String nombre = doc.getString("nombre"); // Cambia "nombre" si tu campo tiene otro nombre
                    if (nombre != null) {
                        userList.add(nombre);
                    }
                }

                adapter.notifyDataSetChanged(); // Actualiza la lista en pantalla
            } else {
                // Manejar error
            }
        });
    }
}