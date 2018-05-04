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
 * Actividad que define el paso del registro donde se introduce el nombre del usuario.
 */
public class activity_registration_name extends AppCompatActivity implements View.OnClickListener {
    /*------------------------------ATRIBUTOS----------------------------*/
    /**
     * Botón para ir al siguiente paso del registro del usuario.
     */
    private Button button_next;
    /**
     * Casillero de texto donde se encuentra el nombre del usuario.
     */
    private EditText edit_text_name;
    /**
     * Casillero de texto donde se encuentra los apellidos del usuario.
     */
    private EditText edit_text_surname;

    /*--------------------------ETAPAS---------------------------------*/

    /**
     * Creación de la actividad.
     *
     * @param savedInstanceState El estado de la aplicación en un paquete.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_name);

        button_next = (Button) findViewById(R.id.buttonNextRegistrationName);
        button_next.setOnClickListener(this);

        edit_text_name = (EditText) findViewById(R.id.editTextNameRegistrationName);
        edit_text_surname = (EditText) findViewById(R.id.editTextSurnameRegistrationName);
    }

    /*--------------------------EVENTOS---------------------------------*/

    /**
     * Ejecuta un procedimiento al tocar un elemento de la actividad.
     *
     * @param view Elemento que ha sido tocado.
     */
    @Override
    public void onClick(View view) {
        /*Cogemos los valores de los casilleros.*/
        String name = edit_text_name.getText().toString();
        String surname = edit_text_surname.getText().toString();
        if (!validateForm(name, surname)) {
            return;
        }
        Intent intent = new Intent(activity_registration_name.this, activity_registration_email.class);
        intent.putExtra("name", name);
        intent.putExtra("surname", surname);
        startActivity(intent);
    }

    /*--------------------------MÉTODOS PRIVADOS---------------------------------*/

    /**
     * Comprueba que los casilleros no estén vacíos.
     *
     * @return Casilleros correctos
     */
    private boolean validateForm(String name, String surname) {
        boolean correct = true;

        if (TextUtils.isEmpty(name)) {
            edit_text_name.setError("Requerido.");
            correct = false;
        } else {
            edit_text_name.setError(null);
        }

        if (TextUtils.isEmpty(surname)) {
            edit_text_surname.setError("Requerido.");
            correct = false;
        } else {
            edit_text_surname.setError(null);
        }
        return correct;
    }
}
