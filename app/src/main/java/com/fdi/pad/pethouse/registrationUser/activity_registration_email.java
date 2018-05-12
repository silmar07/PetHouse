package com.fdi.pad.pethouse.registrationUser;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.fdi.pad.pethouse.R;

/**
 * Actividad que define el paso del registro donde se introduce el email del usuario.
 */
public class activity_registration_email extends AppCompatActivity implements View.OnClickListener {
    /*------------------------------ATRIBUTOS----------------------------*/
    /**
     * Botón para ir al siguiente paso del registro del usuario.
     */
    private Button button_next;
    /**
     * Casillero de texto donde se encuentra el email del usuario.
     */
    private EditText edit_text_email;
    /**
     * Nombre del usuario.
     */
    private String name;
    /**
     * Apellidos del usuario.
     */
    private String surname;

    /*--------------------------ETAPAS---------------------------------*/
    /**
     * Creación de la actividad.
     *
     * @param savedInstanceState El estado de la aplicación en un paquete.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_email);

        button_next = (Button) findViewById(R.id.buttonNextRegistrationEmail);
        button_next.setOnClickListener(this);

        edit_text_email = (EditText) findViewById(R.id.editTextEmailRegistrationEmail);

        /*Recibimos los datos del intent.*/
        Bundle b = getIntent().getExtras();

        name = (String) b.getString("name");
        surname = (String) b.getString("surname");
    }

    /*--------------------------EVENTOS---------------------------------*/
    /**
     * Ejecuta un procedimiento al tocar un elemento de la actividad.
     *
     * @param view Elemento que ha sido tocado.
     */
    @Override
    public void onClick(View view) {
        String email = edit_text_email.getText().toString();
        if (!validateForm(email)) {
            return;
        }
        Intent intent = new Intent(activity_registration_email.this, activity_registration_password.class);
        intent.putExtra("name", name);
        intent.putExtra("surname", surname);
        intent.putExtra("email", email);
        startActivity(intent);
    }

    /*--------------------------MÉTODOS PRIVADOS---------------------------------*/
    /**
     * Comprueba que los casilleros no estén vacíos.
     *
     * @return Casilleros correctos
     */
    private boolean validateForm(String email) {
        boolean correct = true;

        if (TextUtils.isEmpty(email)) {
            edit_text_email.setError("Requerido.");
            correct = false;
        } else if (Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            edit_text_email.setError(null);
        }
        else {
            edit_text_email.setError("Formato de correo incorrecto.");
            correct = false;
        }

        return correct;
    }
}