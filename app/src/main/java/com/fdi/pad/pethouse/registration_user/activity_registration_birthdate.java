package com.fdi.pad.pethouse.registration_user;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.fdi.pad.pethouse.R;
import com.fdi.pad.pethouse.activity_login;

public class activity_registration_birthdate extends AppCompatActivity implements View.OnClickListener{
    /*------------------------------ATRIBUTOS----------------------------*/
    /**
     * Botón para ir al siguiente paso del registro del usuario.
     */
    private Button button_next;
    /**
     * Casillero de texto donde se encuentra el email del usuario.
     */
    private EditText edit_text_birthdate;
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
    /**
     * Contraseña del usuario.
     */
    private String password;

    /*--------------------------ETAPAS---------------------------------*/
    /**
     * Creación de la actividad.
     *
     * @param savedInstanceState El estado de la aplicación en un paquete.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_birthdate);

        button_next = (Button) findViewById(R.id.buttonNextRegistrationBirthdate);
        button_next.setOnClickListener(this);

        edit_text_birthdate = (EditText) findViewById(R.id.editTextBirthdateRegistrationBirthdate);

        /*Recibimos los datos del intent.*/
        Bundle b = getIntent().getExtras();

        name = (String) b.getString("name");
        surname = (String) b.getString("surname");
        email = (String) b.getString("email");
        password = (String) b.getString("password");
    }

    /*--------------------------EVENTOS---------------------------------*/
    /**
     * Ejecuta un procedimiento al tocar un elemento de la actividad.
     *
     * @param view Elemento que ha sido tocado.
     */
    @Override
    public void onClick(View view) {
        String birthdate = edit_text_birthdate.getText().toString();
        if (!validateForm(birthdate)) {
            return;
        }
        Intent intent = new Intent(activity_registration_birthdate.this, activity_login.class);
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
    private boolean validateForm(String birthdate) {
        boolean correct = true;

        if (TextUtils.isEmpty(birthdate)) {
            edit_text_birthdate.setError("Requerido.");
            correct = false;
        } else {
            edit_text_birthdate.setError(null);
        }
        return correct;
    }
}