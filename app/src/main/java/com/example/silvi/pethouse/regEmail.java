package com.example.silvi.pethouse;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class regEmail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg_email);


        //recibimos datos
        Bundle b =getIntent().getExtras();

        final String datoNombre =   b.getString("nombre");
        final String datoApellidos =  (String) b.getString("apellidos");

        Button button = findViewById(R.id.btnSigEmail);

        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                EditText email = (EditText) findViewById(R.id.textoPersonEmail);
                email.toString().trim();

                if(email.equals("")){

                    Toast mensaje =
                            Toast.makeText(getApplicationContext(),
                                    "Faltan datos", Toast.LENGTH_LONG);

                    mensaje.show();
                }else{

                    Intent intent = new Intent(regEmail.this, regContr.class);
                    intent.putExtra("nombre",datoNombre);
                    intent.putExtra("nombre",datoApellidos);
                    intent.putExtra("email",email.getText());
                    startActivity(intent);

                }
            }
        });

    }
}
