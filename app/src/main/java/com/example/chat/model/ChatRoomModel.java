package com.example.chat.model;

import com.google.firebase.Timestamp;
import java.util.List;

public class ChatRoomModel {
    private String nombre;
    private String ultimoMensaje;
    private Timestamp timestamp;
    private List<String> usuarios;

    public ChatRoomModel() {}

    public ChatRoomModel(String nombre, String ultimoMensaje, Timestamp timestamp, List<String> usuarios) {
        this.nombre = nombre;
        this.ultimoMensaje = ultimoMensaje;
        this.timestamp = timestamp;
        this.usuarios = usuarios;
    }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getUltimoMensaje() { return ultimoMensaje; }
    public void setUltimoMensaje(String ultimoMensaje) { this.ultimoMensaje = ultimoMensaje; }

    public Timestamp getTimestamp() { return timestamp; }
    public void setTimestamp(Timestamp timestamp) { this.timestamp = timestamp; }

    public List<String> getUsuarios() { return usuarios; }
    public void setUsuarios(List<String> usuarios) { this.usuarios = usuarios; }
}
