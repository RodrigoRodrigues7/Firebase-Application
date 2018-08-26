package com.example.android.first_firebase_application.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.android.first_firebase_application.R;
import com.google.firebase.auth.FirebaseAuth;

public class TelaPrincipal extends AppCompatActivity {

    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        auth = FirebaseAuth.getInstance();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_admin, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if(id == R.id.action_addUser) {
            openUserRegisterScreen();
        } else if(id == R.id.action_exitAdmin) {
            signOut();
        }

        return super.onOptionsItemSelected(item);
    }

    private void openUserRegisterScreen() {
        Intent intent = new Intent(TelaPrincipal.this, CadastroUsuario.class);
        startActivity(intent);
    }

    private void signOut() {

        auth.signOut();

        Intent intent = new Intent(TelaPrincipal.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

}
