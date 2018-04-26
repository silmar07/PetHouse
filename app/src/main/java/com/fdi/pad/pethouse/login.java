package com.fdi.pad.pethouse;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class login extends AppCompatActivity implements View.OnClickListener {

    Toast message = null;
    private Button login, enter;
    private EditText email, cont;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

         login = (Button) findViewById(R.id.register_button);
         login.setOnClickListener(this);

         enter = (Button) findViewById(R.id.enter_button);
         enter.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.register_button:

                Toast.makeText(getApplicationContext(),"LOGIIIN", Toast.LENGTH_LONG);
                message.show();

                Intent intent = new Intent(login.this, regNombre.class);
                startActivity(intent);

                break;

            case R.id.enter_button:

                 email = (EditText) findViewById(R.id.email_input);
                 cont =  (EditText) findViewById(R.id.password_input);
                //Toast message = null;

                Toast.makeText(getApplicationContext(),"ENTRAAAAR", Toast.LENGTH_LONG);
                message.show();

                if(email.equals("") || cont.equals("") ){

                     Toast.makeText(getApplicationContext(),"Debe rellenar todos los cmapos", Toast.LENGTH_LONG);
                     message.show();
                }else{

                    Toast.makeText(getApplicationContext(), "Aplicacion", Toast.LENGTH_SHORT);
                    message.show();
                }
                break;

        }//Switch

    }//onclick
}

