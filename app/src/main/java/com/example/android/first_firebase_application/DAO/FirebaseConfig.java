package com.example.android.first_firebase_application.DAO;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class FirebaseConfig {

    private static DatabaseReference firebaseReference;
    private static FirebaseAuth authentication;
    private static FirebaseStorage storage;
    private static StorageReference storageReference;

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

    public static FirebaseStorage getFirebaseStorage() {

        if (storage == null) {
            storage = FirebaseStorage.getInstance();
        }

        return storage;
    }

    public static  StorageReference getFirebaseStorageReference() {

        if (storageReference == null) {
            storageReference = FirebaseStorage.getInstance().getReference();
        }

        return storageReference;
    }

}
