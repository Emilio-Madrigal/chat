package com.example.chat.model;

import com.google.firebase.firestore.PropertyName;

import java.util.List;

public class ChatRoomModel {
    private String ultimoMensaje;
    private long timestamp;
    private List<String> usuarios;  // Este es el nombre que usarás en Java

    public ChatRoomModel() {}  // Constructor vacío requerido por Firestore

    public String getUltimoMensaje() {
        return ultimoMensaje;
    }

    public void setUltimoMensaje(String ultimoMensaje) {
        this.ultimoMensaje = ultimoMensaje;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    // Mapear 'users' de Firestore a 'usuarios' en Java
    @PropertyName("users")
    public List<String> getUsuarios() {
        return usuarios;
    }

    @PropertyName("users")
    public void setUsuarios(List<String> usuarios) {
        this.usuarios = usuarios;
    }
}
