package com.example.android.first_firebase_application.Activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.android.first_firebase_application.Classes.User;
import com.example.android.first_firebase_application.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class TelaPrincipal extends AppCompatActivity {

    private FirebaseAuth auth;
    private DatabaseReference reference;

    private TextView userType;
    private User user;
    private String userEmailType;
    private Menu menu1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        auth = FirebaseAuth.getInstance();
        reference = FirebaseDatabase.getInstance().getReference();
        userType = findViewById(R.id.txt_userTitle);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //Limpando as opções do 'menu'.
        menu.clear();
        this.menu1 = menu;

        //Recebendo o email do usuário logado no momento.
        String email = auth.getCurrentUser().getEmail().toString();

        //Ordenando todos os 'emails' do nó 'users' e selecionando o email igual ao email do usuário logado no momento.
        reference.child("users").orderByChild("email").equalTo(email.toString()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                //Percorrendo todos os emails armazenados no dataSpanshot, e
                //atribuindo a variável 'userEmailType' o tipo de usuário que tem no email.
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    userEmailType = postSnapshot.child("userType").getValue().toString();

                    userType.setText(userEmailType);
                    menu1.clear();

                    if (userEmailType.equals("Admin")) {
                        getMenuInflater().inflate(R.menu.menu_admin, menu1);
                    } else if (userEmailType.equals("Attendant")) {
                        getMenuInflater().inflate(R.menu.menu_attendant, menu1);
                    }

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_addUser) {
            openUserRegisterScreen();
        } else if (id == R.id.action_exitAdmin) {
            signOut();
        } else if (id == R.id.action_exitAttendant) {
            signOut();
        } else if (id == R.id.action_cad_Photo_attendant_profile) {
            uploadProfilePhoto();
        }

        return super.onOptionsItemSelected(item);
    }

    private void openUserRegisterScreen() {
        Intent intent = new Intent(TelaPrincipal.this, CadastroUsuario.class);
        startActivity(intent);
    }

    private void uploadProfilePhoto() {

        Intent intent = new Intent(TelaPrincipal.this, UploadPhoto.class);
        startActivity(intent);
        finish();

    }

    private void signOut() {

        auth.signOut();

        Intent intent = new Intent(TelaPrincipal.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

}
