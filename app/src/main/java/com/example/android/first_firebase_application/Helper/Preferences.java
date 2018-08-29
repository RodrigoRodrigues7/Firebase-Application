package com.example.android.first_firebase_application.Helper;

import android.content.Context;
import android.content.SharedPreferences;

//Esta classe irá salvar, no celular, as preferencias do usuário logado no app
//Nome do Arquivo = "app.preferences"
public class Preferences {

    private Context context;
    private SharedPreferences preferences;
    private String FILE_NAME = "app.preferences";
    private int MODE = 0; //Para editar o arquivo(FILE_NAME) que ficará salvo no celular
    private SharedPreferences.Editor editor;

    private final String LOGGED_USER_EMAIL = "logged_UserEmail";
    private final String LOGGED_USER_PASSWORD = "logged_UserPassword";

    public Preferences(Context parameterContext) {
        context = parameterContext;
        preferences = context.getSharedPreferences(FILE_NAME, MODE);

        //Associar o nosso 'preference.edit()' com  o editor
        editor = preferences.edit();
    }

    public void saveUserPreferences(String email, String password) {

        //Salvar no arquivo de preferencias o email e a senha do usuário
        editor.putString(LOGGED_USER_EMAIL, email);
        editor.putString(LOGGED_USER_PASSWORD, password);
        editor.commit();

    }

    public String getLoggedUserEmail() {
        return preferences.getString(LOGGED_USER_EMAIL, null);
    }

    public String getLoggedUserPassword() {
        return preferences.getString(LOGGED_USER_PASSWORD, null);
    }

}
