package com.fdi.pad.pethouse;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class regEdad extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.fdi.pad.pethouse.R.layout.activity_reg_edad);

        //recibimos datos
        Bundle b =getIntent().getExtras();

        final String datoNombre =   b.getString("nombre");
        final String datoApellidos =  (String) b.getString("apellidos");
        final String datoEmail =  (String) b.getString("email");
        final String datoCont =  (String) b.getString("contra");

        Button button = findViewById(com.fdi.pad.pethouse.R.id.btnSigEdad);

        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {


                EditText edad = (EditText) findViewById(com.fdi.pad.pethouse.R.id.textoPersonEdad);
                edad.toString().trim();

                if(edad.equals("")){

                    Toast mensaje =
                            Toast.makeText(getApplicationContext(),
                                    "Faltan datos", Toast.LENGTH_LONG);

                    mensaje.show();
                }else{

                    Intent intent = new Intent(regEdad.this, main.class);
                    intent.putExtra("nombre",datoNombre);
                    intent.putExtra("nombre",datoApellidos);
                    intent.putExtra("email",datoEmail);
                    intent.putExtra("contra",datoCont);
                    intent.putExtra("edad",edad.getText());
                    startActivity(intent);
                }
            }
        });

    }
}
