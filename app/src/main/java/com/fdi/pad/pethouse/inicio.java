package com.fdi.pad.pethouse;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class inicio extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.fdi.pad.pethouse.R.layout.activity_inicio);

        Button button = findViewById(com.fdi.pad.pethouse.R.id.btnReg);

        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Intent intent = new Intent(inicio.this, regNombre.class);
                startActivity(intent);
            }
        });

    }
}
