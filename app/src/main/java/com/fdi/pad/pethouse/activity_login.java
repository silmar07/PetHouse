package com.fdi.pad.pethouse;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * Actividad que define la pantalla inicial de login.
 */
public class activity_login extends AppCompatActivity implements View.OnClickListener {
    /*------------------------------ATRIBUTOS----------------------------*/
    /**
     * Etiqueta para los logs de inicio de sesión.
     */
    private static final String TAG = "Login";
    /**
     * Botón para iniciar el registro de un usuario.
     */
    private Button button_register;
    /**
     * Botón para iniciar la sesión del usuario.
     */
    private Button button_enter;
    /**
     * Casillero de texto donde se encuentra el email del usuario.
     */
    private EditText edit_text_email;
    /**
     * Casillero de texto donde se encuentra la contraseña del usuario.
     */
    private EditText edit_text_password;
    /**
     * Autencicador de la aplicación dado por la tecnología FireBase.
     */
    private FirebaseAuth my_authentication;

    /*--------------------------ETAPAS---------------------------------*/
    /**
     * Creación de la actividad.
     * @param savedInstanceState El estado de la aplicación en un paquete.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        my_authentication = FirebaseAuth.getInstance();

        button_register = (Button) findViewById(R.id.buttonRegisterLogin);
        button_register.setOnClickListener(this);
        button_enter = (Button) findViewById(R.id.buttonEnterLogin);
        button_enter.setOnClickListener(this);

        edit_text_email = (EditText) findViewById(R.id.editTextEmailLogin);
        edit_text_password = (EditText) findViewById(R.id.editTextPasswordLogin);
    }

    /**
     * La actividad comienza a ejecutarse.
     */
    @Override
    public void onStart() {
        super.onStart();
        /*Comprobamos si hay algún usuario que ya ha iniciado la sesión.*/
        FirebaseUser current_user = my_authentication.getCurrentUser();
        /*En caso de que se haya iniciado, se procede a mostrar la pantalla principal.*/
        if (current_user != null) {
            login(current_user);
        }
    }

    /*--------------------------EVENTOS---------------------------------*/
    /**
     * Ejecuta un procedimiento al tocar un elemento de la actividad.
     * @param v Elemento que ha sido tocado.
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonRegisterLogin:
                RegisterButton();
                break;
            case R.id.buttonEnterLogin:
                EnterButton();
                break;
        }
    }

    /*--------------------------MÉTODOS PRIVADOS---------------------------------*/
    /**
     * Procede a cambiar a la actividad "home".
     * @param current_user Usuario logueado.
     */
    private void login(FirebaseUser current_user) {
        Intent intent = new Intent(activity_login.this, activity_home.class);
        startActivity(intent);
    }
    /**
     * Comienza el flujo del registro de un usuario.
     */
    private void RegisterButton() {
        Intent intent = new Intent(activity_login.this, regNombre.class);
        startActivity(intent);
    }
    /**
     * Si los valores son válidos, loguea al usuario.
     */
    private void EnterButton() {
        /*Cogemos los valores de los casilleros.*/
        String email = edit_text_email.getText().toString();
        String password = edit_text_password.getText().toString();
        if (!validateForm(email, password)) {
            return;
        }
        /*Ejecutamos la autentificación del usuario.*/
        my_authentication.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = my_authentication.getCurrentUser();
                            login(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(activity_login.this, "Autentificación fallida.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    /**
     * Comprueba que los casilleros no estén vacíos.
     * @return Casilleros correctos
     */
    private boolean validateForm(String email, String password) {
        boolean correct = true;

        if (TextUtils.isEmpty(email)) {
            edit_text_email.setError("Requerido.");
            correct = false;
        } else {
            edit_text_email.setError(null);
        }

        if (TextUtils.isEmpty(password)) {
            edit_text_password.setError("Requerido.");
            correct = false;
        } else {
            edit_text_password.setError(null);
        }
        return correct;
    }
}

