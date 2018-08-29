package com.example.android.first_firebase_application.Activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.android.first_firebase_application.DAO.FirebaseConfig;
import com.example.android.first_firebase_application.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;

public class UploadPhoto extends AppCompatActivity {

    private ImageView imageView;
    private Button btnUpload;
    private Button btnCancel;

    private StorageReference storageReference;
    private FirebaseDatabase database;
    private DatabaseReference firebaseReference;
    private FirebaseAuth auth;

    private String loggedUserEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_photo);

        auth = FirebaseConfig.getFirebaseAuth();
        storageReference = FirebaseConfig.getFirebaseStorageReference();
        loggedUserEmail = auth.getCurrentUser().getEmail();

        imageView = findViewById(R.id.img_attendantProfilePhoto);
        btnUpload = findViewById(R.id.btn_uploadPhoto);
        btnCancel = findViewById(R.id.btn_Cancel);

        loadDefaultImage();

        // Ao clicar no imageView ele abre um menu de opção para escolher a foto
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                startActivityForResult(Intent.createChooser(intent, "Choose any Photo you Would Like"), 123);
            }
        });

        // Ao clicar no botão, a foto selecionada será enviada para o Storage do Firebase
        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerAttendantPhoto();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        final int width = 220;
        final int height = 220;

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == 123) {
                Uri selectedImage = data.getData();
                Picasso.with(UploadPhoto.this).load(selectedImage.toString()).resize(width, height).centerCrop().into(imageView);
            }
        }

    }

    //Método que fara o Upload da imagem no aparelho para o Storage do Firebase, e criará a pasta 'userProfilePhotos/'
    private void registerAttendantPhoto() {

        StorageReference buildReferencePhoto = storageReference.child("userProfilePhotos/" + loggedUserEmail + ".jpg");

        imageView.setDrawingCacheEnabled(true);
        imageView.buildDrawingCache();

        Bitmap bitmap = imageView.getDrawingCache();

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);

        byte[] data = outputStream.toByteArray();
        UploadTask uploadTask = buildReferencePhoto.putBytes(data);

        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                Uri downloadUrl = taskSnapshot.getUploadSessionUri();
                loadDefaultImage();
            }
        });

    }

    //Método que carrega uma imagem padrão no imageView
    private void loadDefaultImage() {

        FirebaseStorage storage = FirebaseStorage.getInstance();
        final StorageReference storageReference = storage.getReferenceFromUrl("gs://first-firebase-applicati-78317.appspot.com/picture.png");

        final int width = 220;
        final int height = 220;

        storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {

                Picasso.with(UploadPhoto.this).load(uri.toString()).resize(width, height).centerCrop().into(imageView);

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });

    }

}


















