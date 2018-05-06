package com.fdi.pad.pethouse.registration_user;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.fdi.pad.pethouse.R;
import com.fdi.pad.pethouse.activity_login;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.chrono.ChronoLocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class activity_registration_birthdate extends AppCompatActivity implements View.OnClickListener{
    /*------------------------------ATRIBUTOS----------------------------*/

    public static final String TAG = "Register";
    /**
     * Botón para ir al siguiente paso del registro del usuario.
     */
    private Button button_next;
    /**
     * Botón para ir a escoger una fecha.
     */
    private Button button_date;
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

    private int year;

    private int month;

    private int day;

    private static final int TYPE_DIALOG = 0;

    private static DatePickerDialog.OnDateSetListener listener_select_date;
    /**
     * Autencicador de la aplicación dado por la tecnología FireBase.
     */
    private FirebaseAuth my_authentication;


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

        my_authentication = FirebaseAuth.getInstance();

        button_next = (Button) findViewById(R.id.buttonNextRegistrationBirthdate);
        button_next.setOnClickListener(this);
        button_date = (Button) findViewById(R.id.buttonDateRegistrationBirthdate);
        button_date.setOnClickListener(this);

        edit_text_birthdate = (EditText) findViewById(R.id.editTextBirthdateRegistrationBirthdate);

        /*Recibimos los datos del intent.*/
        Bundle b = getIntent().getExtras();

        name = (String) b.getString("name");
        surname = (String) b.getString("surname");
        email = (String) b.getString("email");
        password = (String) b.getString("password");

        Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        updateDate();
        listener_select_date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int y, int m, int dayOfMonth) {
                year = y;
                month = m + 1;
                day = dayOfMonth;
                updateDate();
            }
        };
    }

    /*--------------------------EVENTOS---------------------------------*/
    /**
     * Ejecuta un procedimiento al tocar un elemento de la actividad.
     *
     * @param view Elemento que ha sido tocado.
     */
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.buttonNextRegistrationBirthdate:
                NextButton();
                break;
            case R.id.buttonDateRegistrationBirthdate:
                Datepicker();
                break;
        }
    }

    @Override
    protected Dialog onCreateDialog(int id){
        switch (id){
            case TYPE_DIALOG:
                return new DatePickerDialog(this, listener_select_date, year, month, day);
        }
        return null;
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
        } else if(yearOld() >= 18){
            edit_text_birthdate.setError(null);
        }
        else {
            edit_text_birthdate.setError("Requieres ser mayor de edad");
            correct = false;
        }
        return correct;
    }

    private int yearOld() {
        int age = 0;
        try {

        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        Date birthdate = dateFormat.parse(day +"/"+month+"/"+year);
        Calendar calendar = Calendar.getInstance();
        Date currentdate = calendar.getTime();

        DateFormat formatter = new SimpleDateFormat("yyyyMMdd");
        int from = Integer.parseInt(formatter.format(birthdate));
        int to = Integer.parseInt(formatter.format(currentdate));

        age = (to - from) / 10000;

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return age;
    }

    private void updateDate(){
        edit_text_birthdate.setText(day + "/" + month + "/" + year);
    }

    private void Datepicker() {
        showDialog(TYPE_DIALOG);
    }

    private void NextButton() {
        String birthdate = edit_text_birthdate.getText().toString();
        if (!validateForm(birthdate)) {
            return;
        }
        my_authentication.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {

                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user_session = my_authentication.getCurrentUser();
                            Intent intent = new Intent(activity_registration_birthdate.this, activity_login.class);
                            startActivity(intent);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(activity_registration_birthdate.this, "Autentificación fallida.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}