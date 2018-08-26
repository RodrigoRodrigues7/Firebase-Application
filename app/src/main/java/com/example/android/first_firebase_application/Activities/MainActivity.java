package com.example.android.first_firebase_application.Activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.android.first_firebase_application.Classes.User;
import com.example.android.first_firebase_application.DAO.FirebaseConfig;
import com.example.android.first_firebase_application.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth authentication;
    private EditText edtEmailLogin;
    private EditText edtPasswordLogin;
    private Button btnLogin;
    private ProgressBar spinner;

    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtEmailLogin = findViewById(R.id.edt_Email);
        edtPasswordLogin = findViewById(R.id.edt_Password);
        btnLogin = findViewById(R.id.btn_Log_in);

        spinner = findViewById(R.id.progressBarID);
        spinner.setVisibility(View.GONE);

        if (verifyLoggedUser()) {

            Intent intentAccount = new Intent(MainActivity.this, TelaPrincipal.class);
            openNewActivity(intentAccount);

        } else {

            btnLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (!edtEmailLogin.getText().toString().trim().equals("") && !edtPasswordLogin.getText().toString().trim().equals("")) {

                        user = new User();

                        user.setEmail(edtEmailLogin.getText().toString());
                        user.setPassword(edtPasswordLogin.getText().toString());

                        validateLogin();
                        spinner.setVisibility(View.VISIBLE);

                    } else {
                        Toast.makeText(MainActivity.this, "Please, Fill in the E-mail and Password Fields!", Toast.LENGTH_SHORT).show();
                    }

                }
            });

        }

    }

    //Método para autenticar o login do usuário
    private void validateLogin() {

        authentication = FirebaseConfig.getFirebaseAuth();
        authentication.signInWithEmailAndPassword(user.getEmail().toString(), user.getPassword().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()) {

                    openMainScreen();
                    Toast.makeText(MainActivity.this, "Successful Log-In", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(MainActivity.this, "Invalid User or Password, Try Again!", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    //Método que inicia a activity de tela Principal
    private void openMainScreen() {

        Intent intent = new Intent(MainActivity.this, TelaPrincipal.class);
        startActivity(intent);
    }

    //Método para verificar se o usuário já está logado
    public Boolean verifyLoggedUser() {

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            return true;
        } else {
            return false;
        }

    }

    //Método genérico para abrir uma activity
    public void openNewActivity(Intent intent) {
        startActivity(intent);
    }

}
