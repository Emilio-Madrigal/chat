package com.example.chat;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class ChatRoomActivity extends AppCompatActivity {
    EditText barra;
    ImageButton salir;
    ImageButton enviar;
    TextView nombreuser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_chat_room);

        barra=findViewById (R.id.input_text);
        salir=findViewById (R.id.back_btn);
        enviar=findViewById (R.id.boton_enviar_texto);
        nombreuser=findViewById (R.id.userNameName);


        String chatroomid = getIntent().getStringExtra("chatroomid");
        String otroUsuarioUid = getIntent().getStringExtra("otrousuario");





        // Aquí ya puedes usar esos valores para cargar los mensajes
    }

}