package com.fdi.pad.pethouse.registration_user;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.fdi.pad.pethouse.R;

/**
 * Actividad que define el paso del registro donde se introduce la contraseña del usuario.
 */
public class activity_registration_password extends AppCompatActivity implements View.OnClickListener {
    /*------------------------------ATRIBUTOS----------------------------*/
    /**
     * Botón para ir al siguiente paso del registro del usuario.
     */
    private Button button_next;
    /**
     * Casillero de texto donde se encuentra el email del usuario.
     */
    private EditText edit_text_password;
    /**
     * Nombre del usuario.
     */
    private String name;
    /**
     * Apellidos del usuario.
     */
    private String surname;
    /**
     * Correo electrónico del usuario.
     */
    private String email;

    /*--------------------------ETAPAS---------------------------------*/
    /**
     * Creación de la actividad.
     *
     * @param savedInstanceState El estado de la aplicación en un paquete.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_password);

        button_next = (Button) findViewById(R.id.buttonNextRegistrationPassword);
        button_next.setOnClickListener(this);

        edit_text_password = (EditText) findViewById(R.id.editTextPasswordRegistrationPassword);

        /*Recibimos los datos del intent.*/
        Bundle b = getIntent().getExtras();

        name = (String) b.getString("name");
        surname = (String) b.getString("surname");
        email = (String) b.getString("email");
    }

    /*--------------------------EVENTOS---------------------------------*/
    /**
     * Ejecuta un procedimiento al tocar un elemento de la actividad.
     *
     * @param view Elemento que ha sido tocado.
     */
    @Override
    public void onClick(View view) {
        String password = edit_text_password.getText().toString();
        if (!validateForm(password)) {
            return;
        }
        Intent intent = new Intent(activity_registration_password.this, activity_registration_birthdate.class);
        intent.putExtra("name", name);
        intent.putExtra("surname", surname);
        intent.putExtra("email", email);
        intent.putExtra("password", password);
        startActivity(intent);
    }

    /*--------------------------MÉTODOS PRIVADOS---------------------------------*/
    /**
     * Comprueba que los casilleros no estén vacíos.
     *
     * @return Casilleros correctos
     */
    private boolean validateForm(String password) {
        boolean correct = true;

        if (TextUtils.isEmpty(password)) {
            edit_text_password.setError("Requerido.");
            correct = false;
        } else {
            edit_text_password.setError(null);
        }
        return correct;
    }
}