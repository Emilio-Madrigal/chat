package com.example.chat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Username extends AppCompatActivity {
    EditText username;
    Button finalizar;
    ProgressBar userbar;
    FirebaseFirestore db; // Firestore instance

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_username);

        username = findViewById(R.id.user);
        finalizar = findViewById(R.id.finalizar);
        userbar = findViewById(R.id.progressBarUser);

        db = FirebaseFirestore.getInstance();


        finalizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nombreUsuario = username.getText().toString().trim();
                if (!nombreUsuario.isEmpty()) {
                    userbar.setVisibility(View.VISIBLE);

                    Map<String, Object> user = new HashMap<>();
                    user.put("nombre", nombreUsuario);

                    db.collection("usuarios")
                            .add(user)
                            .addOnSuccessListener(documentReference -> {
                                userbar.setVisibility(View.GONE);
                                Intent intent = new Intent(Username.this, StartActivity.class);
                                startActivity(intent);
                                finish();
                            })
                            .addOnFailureListener(e -> {
                                userbar.setVisibility(View.GONE);
                            });
                }
            }
        });
    }
}