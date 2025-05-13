package com.example.chat.model;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.PropertyName;
import java.util.List;

public class ChatRoomModel {

    private String ultimoMensaje;
    private Timestamp timestamp;
    private List<String> usuarios;

    // 🔧 Constructor vacío requerido por Firestore
    public ChatRoomModel() {
    }

    public ChatRoomModel(List<String> usuarios) {
        this.usuarios = usuarios;
    }

    public String getUltimoMensaje() {
        return ultimoMensaje;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    @PropertyName("users")
    public List<String> getUsuarios() {
        return usuarios;
    }

    @PropertyName("users")
    public void setUsuarios(List<String> usuarios) {
        this.usuarios = usuarios;
    }
}