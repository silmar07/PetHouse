package com.fdi.pad.pethouse;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.fdi.pad.pethouse.entities.Pet;
import com.fdi.pad.pethouse.entities.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class modMascota extends AppCompatActivity {

    public static final String PET_EXTRA = "PET_EXTRA";
    private String idPet;
    private Pet pet;
    private static final String TAG = "Home pet";

    private EditText nom,espe,edad,raza,est,datos,otros;
    private FirebaseAuth my_authentication;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mod_mascota);

        pet = getIntent().getParcelableExtra(PET_EXTRA);
        idPet = getIntent().getStringExtra("uidPet");

        actualizarDatos();

        nom = findViewById(R.id.modMasNombre);
        espe = findViewById(R.id.modEspecieMasNombre);
        edad = findViewById(R.id.modEdadMasNombre);
        raza = findViewById(R.id.modRazaMasNombre);
        est = findViewById(R.id.modEsteMasNombre);
        datos = findViewById(R.id.modDatosMedMasNombre);
        otros = findViewById(R.id.modOtroDatosMasNombre);

        nom.setText(pet.getName());
        espe.setText(pet.getSpecies());
        edad.setText(pet.getAge());
        raza.setText(pet.getBreed());
        est.setText(pet.getStelilization());
        datos.setText(pet.getMedicalData());
        otros.setText(pet.getOtherData());


        Button btn= findViewById(R.id.btnMasGuardar);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View view){
                DatabaseReference database = FirebaseDatabase.getInstance().getReference();
                FirebaseAuth edit = FirebaseAuth.getInstance();

                my_authentication = FirebaseAuth.getInstance();

                Pet pet = new Pet(nom.getText().toString(),espe.getText().toString(),raza.getText().toString(),edad.getText().toString(),
                        est.getText().toString(),datos.getText().toString(),otros.getText().toString());

                database.child("pets").child(my_authentication.getCurrentUser().getUid()).child(idPet).setValue(pet);

                Intent data  = new Intent();
                data.putExtra(PET_EXTRA, pet);
                setResult(Activity.RESULT_OK, data);
                finish();

            }
        });

    }

    private void actualizarDatos(){

        FirebaseAuth my_authentication = FirebaseAuth.getInstance();
        my_authentication.getCurrentUser().getUid();

        FirebaseDatabase.getInstance().getReference("pets").child(my_authentication.getCurrentUser().getUid()).child(idPet)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        pet = dataSnapshot.getValue(Pet.class);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.e(TAG, databaseError.toString());
                    }
                });



    }


}
