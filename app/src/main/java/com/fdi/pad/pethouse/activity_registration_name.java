package com.fdi.pad.pethouse;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class activity_registration_name extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.fdi.pad.pethouse.R.layout.activity_registration_name);


        Button button = findViewById(com.fdi.pad.pethouse.R.id.btnSigNom);

        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                //guaradamos los datos
            EditText nom = (EditText) findViewById(com.fdi.pad.pethouse.R.id.textPersonName);
            EditText ape = (EditText) findViewById(com.fdi.pad.pethouse.R.id.textPersonApe);

            nom.toString().trim();
            ape.toString().trim();
            if(nom.equals("") || ape.equals("")){

                Toast mensaje =
                        Toast.makeText(getApplicationContext(),
                                "Faltan datos", Toast.LENGTH_LONG);

                mensaje.show();

            }else{

                Intent intent = new Intent(activity_registration_name.this, regEmail.class);
                intent.putExtra("nombre",nom.getText());
                intent.putExtra("apellidos",ape.getText());
                startActivity(intent);

            }
            }
        });

    }
}
