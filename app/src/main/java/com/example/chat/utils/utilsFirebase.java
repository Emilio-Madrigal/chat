package com.example.chat.utils;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
public class utilsFirebase {
    public static String userid(){
        return FirebaseAuth.getInstance ().getUid ();
    }
    public static boolean islogged() {
        return FirebaseAuth.getInstance().getCurrentUser() != null;
    }
    public  static DocumentReference currentUserDetails(){
        return FirebaseFirestore.getInstance ().collection ("users").document (userid());
    }
}