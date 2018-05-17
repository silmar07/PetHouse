package com.fdi.pad.pethouse.home;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.fdi.pad.pethouse.R;
import com.fdi.pad.pethouse.entities.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class editarPerfil extends AppCompatActivity {
    public static final String USER_EXTRA = "USER_EXTRA";

    private EditText Editname;
    private EditText Editsurname;
    private EditText EditCumple;
    private EditText EditEmail;
    String surname ="name";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_perfil);

        User user = getIntent().getParcelableExtra(USER_EXTRA);

        this.Editsurname = findViewById(R.id.textSurname);
        this.Editname = findViewById(R.id.textNombre);
        this.EditCumple = findViewById(R.id.textcumpleaños);
        this.EditEmail = findViewById(R.id.textEmail);

        this.EditCumple.setText(user.getBirthdate());
        this.EditEmail.setText(user.getEmail());
        this.Editname.setText(user.getName());
        this.Editsurname.setText(user.getSurname());

        Button btn= findViewById(R.id.btnGuardar);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
                public void onClick (View view){
                DatabaseReference database = FirebaseDatabase.getInstance().getReference();
                    FirebaseAuth edit = FirebaseAuth.getInstance();
                    FirebaseUser fireU =edit.getCurrentUser();
                    User user = new User(fireU.getUid(),Editname.getText().toString(),Editsurname.getText().toString(),EditCumple.getText().toString(),EditEmail.getText().toString());
                    database.child("users").child(fireU.getUid()).setValue(user);

                    Intent data  = new Intent();
                    data.putExtra(USER_EXTRA, user);
                    setResult(Activity.RESULT_OK, data);
                    finish();
                }
            });

    }
}
