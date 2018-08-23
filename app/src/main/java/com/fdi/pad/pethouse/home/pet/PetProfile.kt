package com.fdi.pad.pethouse.home.pet

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import com.fdi.pad.pethouse.R

import com.fdi.pad.pethouse.entities.Pet
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class PetProfile : AppCompatActivity() {

    private var pet: Pet? = null

    private var nom: TextView? = null
    private var espe: TextView? = null
    private var edad: TextView? = null
    private var raza: TextView? = null
    private var est: TextView? = null
    private var datos: TextView? = null
    private var otros: TextView? = null
    private var idPet: String? = null

    @SuppressLint("WrongViewCast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_perfil_mascota)

        //obtenemos el id
        pet = intent.getParcelableExtra(PET_EXTRA)
        idPet = intent.getStringExtra("uidPet")

        nom = findViewById(R.id.perMasNombre)
        espe = findViewById(R.id.perEspecieMasNombre)
        edad = findViewById(R.id.perEdadMasNombre)
        raza = findViewById(R.id.perRazaMasNombre)
        est = findViewById(R.id.perEsteMasNombre)
        datos = findViewById(R.id.perDatosMedMasNombre)
        otros = findViewById(R.id.perOtroDatosMasNombre)

        obtenerDatos()

        val btn = findViewById<Button>(R.id.btnModMas)
        btn.setOnClickListener {
            val intent = Intent(this@PetProfile, PetMod::class.java)
            intent.putExtra("uidPet", idPet)
            intent.putExtra(PetMod.PET_EXTRA, pet)
            startActivityForResult(intent, EDIT_CODE)
        }
    }


    private fun obtenerDatos() {

        val myAuthentication = FirebaseAuth.getInstance()
        myAuthentication.currentUser!!.uid

        FirebaseDatabase.getInstance().getReference("pets").child(myAuthentication.currentUser!!.uid).child(idPet!!)
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        pet = dataSnapshot.getValue(Pet::class.java)
                        updateUI()
                    }

                    override fun onCancelled(databaseError: DatabaseError) {
                        Log.e(TAG, databaseError.toString())
                    }
                })

    }

    private fun updateUI() {
        nom!!.text = pet!!.name
        nom!!.isEnabled = false
        espe!!.text = pet!!.species
        espe!!.isEnabled = false
        edad!!.text = pet!!.age
        edad!!.isEnabled = false
        raza!!.text = pet!!.breed
        raza!!.isEnabled = false
        est!!.text = pet!!.stelilization
        est!!.isEnabled = false
        datos!!.text = pet!!.medicalData
        datos!!.isEnabled = false
        otros!!.text = pet!!.otherData
        otros!!.isEnabled = false
    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        if (requestCode == EDIT_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                pet = data.getParcelableExtra(PetMod.PET_EXTRA)
                updateUI()
            }
        }

        super.onActivityResult(requestCode, resultCode, data)
    }

    companion object {
        const val PET_EXTRA = "PET_EXTRA"
        private const val TAG = "Home pet"
        private const val EDIT_CODE = 1000
    }
}
