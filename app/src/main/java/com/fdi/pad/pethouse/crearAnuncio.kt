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
import com.fdi.pad.pethouse.entities.Ad
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
import kotlinx.android.synthetic.main.activity_crear_anuncio.*
import kotlinx.android.synthetic.main.activity_crear_mascota.*
import kotlinx.android.synthetic.main.activity_registration_birthdate.*
import java.lang.Integer.parseInt

class crearAnuncio : AppCompatActivity() {

    val AD_EXTRA = "AD_EXTRA"

    //datos de la bbdd
    private val nameAd = "name"
    private val urlAd = "url"


    //Atributos
    private var name: String? = null
    private var url: String? = null

    private val databaseAds = "ads"
    private var authentication: FirebaseAuth? = null
    private var database: DatabaseReference? = null
    private var storage: StorageReference? = null
    private var userSession: FirebaseUser? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crear_anuncio)

        btnCrearAdGuardar.setOnClickListener { btnGuardar() }

        authentication = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance().reference
        storage = FirebaseStorage.getInstance().reference

    }


    private fun btnGuardar() {

        //recogemos los datos
        name = crearAdNombre.text.toString();
        url = crearAdUrl.text.toString();


        if(name.equals("") || url.equals("")){
            return
        }else{

            userSession = authentication!!.currentUser
            val ad = Ad(userSession!!.uid,name, url)
            database!!.child(databaseAds).push().setValue(ad)

            val data = Intent()
            data.putExtra(AD_EXTRA, ad)
            setResult(Activity.RESULT_OK, data)
            finish()

        }//else
    }

}
