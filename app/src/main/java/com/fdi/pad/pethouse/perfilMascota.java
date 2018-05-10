package com.fdi.pad.pethouse;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class perfilMascota extends AppCompatActivity {

    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_mascota);

        //Recibimos datos
        Bundle b = getIntent().getExtras();
        //id = (int) b.getInt("idMas");

        Button mod = findViewById(R.id.btnModMas);
        mod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(perfilMascota.this, modMascota.class);
                //pasamso el id de  las mascota
                //intent.putExtra("idMas", id);
                startActivity(intent);
            }
        });
    }
}
