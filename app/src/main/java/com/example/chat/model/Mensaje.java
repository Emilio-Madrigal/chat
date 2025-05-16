package com.example.chat.model;
import com.google.firebase.Timestamp;
public class Mensaje {
    private String message;
    private String uid;
    private Timestamp timestamp;
    public Mensaje() {}
    public Mensaje(String message, String uid, Timestamp timestamp) {
        this.message = message;
        this.uid = uid;
        this.timestamp = timestamp;
    }
    public String getMessage() {
        return message;
    }
    public String getUid() {
        return uid;
    }
    public Timestamp getTimestamp() {
        return timestamp;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public void setUid(String uid) {
        this.uid = uid;
    }
    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }
}