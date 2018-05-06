package com.fdi.pad.pethouse;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class modMascota extends AppCompatActivity {

    private int id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mod_mascota);

        //recibimos los datos
        Bundle b = getIntent().getExtras();
       // id = (int) b.getInt("idMas");

        Button ok = findViewById(R.id.btnMasGuardar);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(modMascota.this, perfilMascota.class);

                //actualizamso en la case de datos

                startActivity(intent);
            }
        });


    }


}
