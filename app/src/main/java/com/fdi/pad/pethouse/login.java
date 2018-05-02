package com.fdi.pad.pethouse;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.fdi.pad.pethouse.database.DatabaseUser;
import com.fdi.pad.pethouse.entities.User;

public class login extends AppCompatActivity implements View.OnClickListener {

    private Button buttonRegister, buttonEnter;
    private EditText editTextEmail, editTextPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        buttonRegister = (Button) findViewById(R.id.register_button);
        buttonRegister.setOnClickListener(this);

        buttonEnter = (Button) findViewById(R.id.enter_button);
        buttonEnter.setOnClickListener(this);

        editTextEmail = (EditText) findViewById(R.id.email_input);
        editTextPassword = (EditText) findViewById(R.id.password_input);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.register_button:
                RegisterButton();
                break;
            case R.id.enter_button:
                EnterButton();
                break;
        }//Switch
    }

    public void RegisterButton() {
        Intent intent = new Intent(login.this, regNombre.class);
        startActivity(intent);
    }

    public void EnterButton() {
        String email = editTextEmail.getText().toString();
        String password = editTextPassword.getText().toString();

        if (!email.equals("") || password.equals("")) {
            new DatabaseLogin().execute(email, password);
        } else {
            Toast.makeText(getApplicationContext(), "Debes rellenar todos los campos", Toast.LENGTH_SHORT).show();
        }
    }

    private class DatabaseLogin extends AsyncTask<String, Void, User> {

        private ProgressDialog dialog = new ProgressDialog(login.this);

        @Override
        protected void onPreExecute() {
            this.dialog.setMessage("Entrando, espera");
            this.dialog.show();
        }

        @Override
        protected User doInBackground(String... strings) {
            return DatabaseUser.checkUser(strings[0], strings[1]);
        }

        @Override
        protected void onPostExecute(User result) {
            this.dialog.dismiss();
            processLogin(result);
        }
    }
    private void processLogin(User user) {

        Intent intent = new Intent(login.this, home.class);
        startActivity(intent);
    }
}

