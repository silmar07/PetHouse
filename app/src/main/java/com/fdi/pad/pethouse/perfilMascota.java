package com.fdi.pad.pethouse;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.fdi.pad.pethouse.entities.Pet;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class perfilMascota extends AppCompatActivity {

    private Pet pet;
    public static final String PET_EXTRA = "PET_EXTRA";
    private static final String TAG = "Home pet";
    private static final int EDIT_CODE = 1000;

    private TextView nom,espe,edad,raza,est,datos,otros;
    private String idPet;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_mascota);

        //obtenemos el id
        pet = getIntent().getParcelableExtra(PET_EXTRA);
        idPet = getIntent().getStringExtra("uidPet");

        nom = findViewById(R.id.perMasNombre);
        espe = findViewById(R.id.perEspecieMasNombre);
        edad = findViewById(R.id.perEdadMasNombre);
        raza = findViewById(R.id.perRazaMasNombre);
        est = findViewById(R.id.perEsteMasNombre);
        datos = findViewById(R.id.perDatosMedMasNombre);
        otros = findViewById(R.id.perOtroDatosMasNombre);

        obtenerDatos();

        Button btn= findViewById(R.id.btnModMas);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View view){
                Intent intent;
                intent = new Intent(perfilMascota.this,modMascota.class);
                intent.putExtra("uidPet", idPet);
                intent.putExtra(modMascota.PET_EXTRA, pet);
                startActivityForResult(intent, EDIT_CODE);

            }
        });
    }


    private void obtenerDatos(){

        FirebaseAuth my_authentication = FirebaseAuth.getInstance();
        my_authentication.getCurrentUser().getUid();

        FirebaseDatabase.getInstance().getReference("pets").child(my_authentication.getCurrentUser().getUid()).child(idPet)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        pet = dataSnapshot.getValue(Pet.class);
                        updateUI();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.e(TAG, databaseError.toString());
                    }
                });

    }

    private void updateUI(){

        nom.setText(pet.getName());
        nom.setEnabled(false);
        espe.setText(pet.getSpecies());
        espe.setEnabled(false);
        edad.setText(pet.getAge());
        edad.setEnabled(false);
        raza.setText(pet.getBreed());
        raza.setEnabled(false);
        est.setText(pet.getStelilization());
        est.setEnabled(false);
        datos.setText(pet.getMedicalData());
        datos.setEnabled(false);
        otros.setText(pet.getOtherData());
        otros.setEnabled(false);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == EDIT_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                pet = data.getParcelableExtra(modMascota.PET_EXTRA);
                updateUI();
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }
}
