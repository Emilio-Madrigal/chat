package com.example.chat.model;

import java.util.Date;
import java.util.List;

public class ChatRoomModel {
    private String ultimoMensaje;
    private List<String> users;
    private Date timestamp;

    public ChatRoomModel() {} // Constructor vacío requerido por Firestore

    public String getUltimoMensaje() {
        return ultimoMensaje;
    }

    public void setUltimoMensaje(String ultimoMensaje) {
        this.ultimoMensaje = ultimoMensaje;
    }

    public List<String> getUsers() {
        return users;
    }

    public void setUsers(List<String> users) {
        this.users = users;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
}
