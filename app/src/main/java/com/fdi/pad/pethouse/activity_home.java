package com.fdi.pad.pethouse;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class activity_home extends AppCompatActivity implements View.OnClickListener {

    private Button button_exit;
    private FirebaseAuth my_authentication;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        my_authentication = FirebaseAuth.getInstance();
        button_exit = (Button) findViewById(R.id.button_exit);
        button_exit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_exit:
                ExitButton();
                break;
        }
    }

    private void ExitButton() {
        my_authentication.signOut();
        Intent intent = new Intent(activity_home.this, activity_login.class);
        startActivity(intent);
    }
}
