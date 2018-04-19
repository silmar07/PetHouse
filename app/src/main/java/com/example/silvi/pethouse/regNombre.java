package com.example.silvi.pethouse;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class regNombre extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg_nombre);


        Button button = findViewById(R.id.btnSigNom);

        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                //guaradamos los datos
            EditText nom = (EditText) findViewById(R.id.textPersonName);
            EditText ape = (EditText) findViewById(R.id.textPersonApe);

            nom.toString().trim();
            ape.toString().trim();
            if(nom.equals("") || ape.equals("")){

                Toast mensaje =
                        Toast.makeText(getApplicationContext(),
                                "Faltan datos", Toast.LENGTH_LONG);

                mensaje.show();

            }else{

                Intent intent = new Intent(regNombre.this, regEmail.class);
                intent.putExtra("nombre",nom.getText());
                intent.putExtra("apellidos",ape.getText());
                startActivity(intent);

            }
            }
        });

    }
}
