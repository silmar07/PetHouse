package com.fdi.pad.pethouse.registration_user;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.fdi.pad.pethouse.regContr;

public class activity_registration_email extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.fdi.pad.pethouse.R.layout.activity_registration_email);


        //recibimos datos
        Bundle b =getIntent().getExtras();

        final String datoNombre =   b.getString("name");
        final String datoApellidos =  (String) b.getString("surname");

        Button button = findViewById(com.fdi.pad.pethouse.R.id.btnSigEmail);

        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                EditText email = (EditText) findViewById(com.fdi.pad.pethouse.R.id.textoPersonEmail);
                email.toString().trim();

                if(email.equals("")){

                    Toast mensaje =
                            Toast.makeText(getApplicationContext(),
                                    "Faltan datos", Toast.LENGTH_LONG);

                    mensaje.show();
                }else{

                    Intent intent = new Intent(activity_registration_email.this, regContr.class);
                    intent.putExtra("nombre",datoNombre);
                    intent.putExtra("nombre",datoApellidos);
                    intent.putExtra("email",email.getText());
                    startActivity(intent);

                }
            }
        });

    }
}
