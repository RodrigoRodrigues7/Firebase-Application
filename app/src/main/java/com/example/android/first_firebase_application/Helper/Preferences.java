package com.example.android.first_firebase_application.Helper;

import android.content.Context;
import android.content.SharedPreferences;

//Esta classe será usada para que quando um admin salvar um novo usuário, ele possa permaneçer logado no sistema
public class Preferences {

    private Context context;
    private SharedPreferences sharedPreferences;
    private String FILE_NAME = "app.preferences";
    private int MODE = 0; //Para editar o arquivo(FILE_NAME) que ficará salvo no celular
    private SharedPreferences.Editor editor;

    private final String LOGGED_USER_EMAIL = "logged_UserEmail";
    private final String LOGGED_USER_PASSWORD = "logged_UserPassword";

    public Preferences(Context parameterContext) {
        this.context = parameterContext;

        //Associar o nosso 'preference.edit()' com  o editor
        editor = sharedPreferences.edit();
    }

    public void saveUserPreferences(String email, String password) {

        //Salvar no arquivo de preferencias o email do usuário e a senha
        editor.putString(LOGGED_USER_EMAIL, email);
        editor.putString(LOGGED_USER_PASSWORD, password);
        editor.commit();

    }

    public String getLoggedUserEmail() {
        return sharedPreferences.getString(LOGGED_USER_PASSWORD, null);
    }

    public String getLoggedUserPassword() {
        return sharedPreferences.getString(LOGGED_USER_PASSWORD, null);
    }

}
