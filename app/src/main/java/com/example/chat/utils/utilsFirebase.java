package com.example.chat.utils;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class utilsFirebase {

    public static String userid(){
        return FirebaseAuth.getInstance ().getUid ();
    }

    public static boolean islogged(){

        if (currentUserDetails ()!=null){

            return true;
        }

        return false;

    }

    public  static DocumentReference currentUserDetails(){
        return FirebaseFirestore.getInstance ().collection ("users").document (userid());
    }
}
