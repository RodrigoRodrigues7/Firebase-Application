package com.example.android.first_firebase_application.Activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.android.first_firebase_application.Classes.User;
import com.example.android.first_firebase_application.DAO.FirebaseConfig;
import com.example.android.first_firebase_application.Helper.Preferences;
import com.example.android.first_firebase_application.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CadastroUsuario extends AppCompatActivity {

    private EditText firstName;
    private EditText lastName;
    private EditText email;
    private EditText password;
    private EditText confirmPassword;
    private RadioButton rbAdmin;
    private RadioButton rbAttendant;

    private Button btnRegister;
    private Button btnCancel;

    private FirebaseAuth auth;
    private FirebaseDatabase database;
    private DatabaseReference reference;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_usuario);

        firstName = findViewById(R.id.edt_usr_first_name);
        lastName = findViewById(R.id.edt_usr_last_name);
        email = findViewById(R.id.edt_usr_Email);
        password = findViewById(R.id.edt_usr_Password_1);
        confirmPassword = findViewById(R.id.edt_usr_Password_2);
        rbAdmin = findViewById(R.id.rb_usr_Admin);
        rbAttendant = findViewById(R.id.rb_usr_attendant);

        btnRegister = findViewById(R.id.btn_Register);
        btnCancel = findViewById(R.id.btn_Cancel);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (password.getText().toString().equals(confirmPassword.getText().toString())) {

                    user = new User();

                    user.setFirstName(firstName.getText().toString());
                    user.setLastName(lastName.getText().toString());
                    user.setEmail(email.getText().toString());
                    user.setPassword(password.getText().toString());

                    if (rbAdmin.isChecked()) {
                        user.setUserType("Admin");
                    } else if (rbAttendant.isChecked()) {
                        user.setUserType("Attendant");
                    }

                    registerNewUser();

                } else {
                    Toast.makeText(CadastroUsuario.this, "Please, Check if your Password is Correct!", Toast.LENGTH_SHORT).show();
                    password.requestFocus();
                }

            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });

    }

    private void registerNewUser() {

        auth = FirebaseConfig.getFirebaseAuth();
        auth.createUserWithEmailAndPassword(user.getEmail(), user.getPassword()).addOnCompleteListener(CadastroUsuario.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()) {

                    insertUser(user);
                    finish();

                    auth.signOut();
                    openMainScreen();

                } else {

                    String error = "";
                    try {
                        throw task.getException();
                    } catch (FirebaseAuthWeakPasswordException e) {
                        error = "Weak Password, Must Have a Minimum of 8 Characters and Contain Letters and Numbers!";
                    } catch (FirebaseAuthInvalidCredentialsException e) {
                        error = "Invalid E-mail, Type Again!";
                    } catch (FirebaseAuthUserCollisionException e) {
                        error = "This E-mail is Already Checked, Type Again!";
                    } catch (Exception e) {
                        error = "Something Went Wrong While Inserting new User!";
                        e.printStackTrace();
                    }

                    Toast.makeText(CadastroUsuario.this, "Error: " + error, Toast.LENGTH_LONG).show();
                }

            }
        });
    }

    private boolean insertUser(User user) {

        try {

            reference = FirebaseConfig.getFirebaseDatabase().child("users");
            reference.push().setValue(user);
            Toast.makeText(CadastroUsuario.this, "User Successfully Registered!", Toast.LENGTH_SHORT).show();
            return true;

        } catch (Exception e) {

            Toast.makeText(CadastroUsuario.this, "Error, While Inserting New User!", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
            return false;
        }

    }

    private void openMainScreen() {

        auth = FirebaseConfig.getFirebaseAuth();
        Preferences preferences = new Preferences(CadastroUsuario.this);

        auth.signInWithEmailAndPassword(preferences.getLoggedUserEmail(), preferences.getLoggedUserPassword()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()) {
                    Intent intent = new Intent(CadastroUsuario.this, TelaPrincipal.class);
                    startActivity(intent);
                    finish();

                } else {

                    Toast.makeText(CadastroUsuario.this, "Falha!", Toast.LENGTH_LONG).show();
                    auth.signOut();

                    Intent intent = new Intent(CadastroUsuario.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }

            }
        });

    }

}
