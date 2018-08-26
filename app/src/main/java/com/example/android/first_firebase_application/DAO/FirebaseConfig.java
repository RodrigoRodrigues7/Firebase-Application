package com.example.android.first_firebase_application.DAO;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FirebaseConfig {

    private static DatabaseReference firebaseReference;
    private static FirebaseAuth authentication;

    public static DatabaseReference getFirebaseDatabase() {

        if (firebaseReference == null) {
            firebaseReference = FirebaseDatabase.getInstance().getReference();
        }
        return firebaseReference;
    }

    public static FirebaseAuth getFirebaseAuth() {

        if (authentication == null) {
            authentication = FirebaseAuth.getInstance();
        }
        return authentication;
    }

}
