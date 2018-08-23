package com.fdi.pad.pethouse.home.pet

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import com.fdi.pad.pethouse.R

import com.fdi.pad.pethouse.entities.Pet
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class PetMod : AppCompatActivity() {
    private var idPet: String? = null
    private var pet: Pet? = null

    private var nom: EditText? = null
    private var espe: EditText? = null
    private var edad: EditText? = null
    private var raza: EditText? = null
    private var est: EditText? = null
    private var datos: EditText? = null
    private var otros: EditText? = null
    private var myAuthentication: FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mod_mascota)

        pet = intent.getParcelableExtra(PET_EXTRA)
        idPet = intent.getStringExtra("uidPet")

        actualizarDatos()

        nom = findViewById(R.id.modMasNombre)
        espe = findViewById(R.id.modEspecieMasNombre)
        edad = findViewById(R.id.modEdadMasNombre)
        raza = findViewById(R.id.modRazaMasNombre)
        est = findViewById(R.id.modEsteMasNombre)
        datos = findViewById(R.id.modDatosMedMasNombre)
        otros = findViewById(R.id.modOtroDatosMasNombre)

        nom!!.setText(pet!!.name)
        espe!!.setText(pet!!.species)
        edad!!.setText(pet!!.age)
        raza!!.setText(pet!!.breed)
        est!!.setText(pet!!.stelilization)
        datos!!.setText(pet!!.medicalData)
        otros!!.setText(pet!!.otherData)


        val btn = findViewById<Button>(R.id.btnMasGuardar)
        btn.setOnClickListener {
            val database = FirebaseDatabase.getInstance().reference

            myAuthentication = FirebaseAuth.getInstance()

            val pet = Pet(nom!!.text.toString(), espe!!.text.toString(), raza!!.text.toString(), edad!!.text.toString(),
                    est!!.text.toString(), datos!!.text.toString(), otros!!.text.toString())

            database.child("pets").child(myAuthentication!!.currentUser!!.uid).child(idPet!!).setValue(pet)

            val data = Intent()
            data.putExtra(PET_EXTRA, pet)
            setResult(Activity.RESULT_OK, data)
            finish()
        }

    }

    private fun actualizarDatos() {

        val myAuthentication = FirebaseAuth.getInstance()
        myAuthentication.currentUser!!.uid

        FirebaseDatabase.getInstance().getReference("pets").child(myAuthentication.currentUser!!.uid).child(idPet!!)
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        pet = dataSnapshot.getValue(Pet::class.java)
                    }

                    override fun onCancelled(databaseError: DatabaseError) {
                        Log.e(TAG, databaseError.toString())
                    }
                })


    }

    companion object {
        const val PET_EXTRA = "PET_EXTRA"
        private const val TAG = "Home pet"
    }
}
