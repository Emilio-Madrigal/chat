package com.example.chat;

public class Mensaje {
    public String contenido;
    public String emisorId;
    public long timestamp;

    public Mensaje() {
        // Constructor vacío necesario para Firebase
    }

    public Mensaje(String contenido, String emisorId, long timestamp) {
        this.contenido = contenido;
        this.emisorId = emisorId;
        this.timestamp = timestamp;
    }

    public String getContenido() {
        return contenido;
    }

    public String getEmisorId() {
        return emisorId;
    }

    public long getTimestamp() {
        return timestamp;
    }
}
