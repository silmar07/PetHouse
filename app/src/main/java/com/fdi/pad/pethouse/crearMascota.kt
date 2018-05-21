package com.fdi.pad.pethouse

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.Toast
import com.fdi.pad.pethouse.R.id.progressBarRegistration
import com.fdi.pad.pethouse.entities.Pet
import com.fdi.pad.pethouse.entities.User
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.android.synthetic.main.activity_crear_mascota.*
import kotlinx.android.synthetic.main.activity_registration_birthdate.*
import java.lang.Integer.parseInt

class crearMascota : AppCompatActivity() {

    val PET_EXTRA = "PET_EXTRA"

    //datos de la bbdd
    private val idUserPet = "idUser"
    private val namePet = "name"
    private val agePet = "age"
    private val speciePet = "specie"
    private val breedPet = "breed"
    private val stelilizationPet = "stelilization"
    private val otherDataPet = "otherData"
    private val medicalDataPet = "medicalData"


    //Atributos
    private var name: String? = null
    private var age: String? = null
    private var specie: String? = null
    private var breed: String? = null
    private var stelilization: String? = null
    private var otherData: String? = null
    private var medicalData: String? = null

    private val databasePets = "pets"
    private var authentication: FirebaseAuth? = null
    private var database: DatabaseReference? = null
    private var storage: StorageReference? = null
    private var userSession: FirebaseUser? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crear_mascota)

        btnCrearMasGuardar.setOnClickListener { btnGuardar() }

        authentication = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance().reference
        storage = FirebaseStorage.getInstance().reference

    }


    private fun btnGuardar() {

        //recogemos los datos
        name = crearMasNombre.text.toString();
        age = crearEdadMasNombre.text.toString();
        specie = crearEspecieMasNombre.text.toString();
        breed = crearRazaMasNombre.text.toString();
        stelilization = crearEsteMasNombre.text.toString();
        otherData = crearOtroDatosMasNombre.text.toString();
        medicalData = crearDatosMedMasNombre.text.toString();


        if(name.equals("") || age.equals("") || specie.equals("") || breed.equals("") || stelilization.equals("") || otherData.equals("")
                || medicalData.equals("")){
            return
        }else{

            userSession = authentication!!.currentUser
            val pet = Pet(name, specie, breed, age,stelilization,medicalData,otherData)
            database!!.child(databasePets).child(userSession!!.uid).push().setValue(pet)

            val data = Intent()
            data.putExtra(PET_EXTRA, pet)
            setResult(Activity.RESULT_OK, data)
            finish()

        }//else
    }

}
