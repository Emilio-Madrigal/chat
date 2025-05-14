package com.example.chat;
import static java.security.AccessController.getContext;
import com.example.chat.utils.utilsFirebase;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.installations.Utils;

public class ChatRoomActivity extends AppCompatActivity {
    EditText barra;
    ImageButton salir;
    ImageButton enviar;
    TextView nombreuser;
    String chatroomid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_chat_room);

        barra=findViewById (R.id.input_text);
        salir=findViewById (R.id.back_btn);
        enviar=findViewById (R.id.boton_enviar_texto);
        nombreuser=findViewById (R.id.userNameName);

        chatroomid= utilsFirebase.getChatroomId (utilsFirebase.userid (),nombreuser.getUse)

        String chatroomid = getIntent().getStringExtra("chatroomid");
        String otroUsuarioUid = getIntent().getStringExtra("otrousuario");

        salir.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (ChatRoomActivity.this,StartActivity.class);
                intent.putExtra ("estado",true);
                startActivity(intent);
            }
        });
    }

}