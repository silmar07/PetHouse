package com.fdi.pad.pethouse.home.home

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.fdi.pad.pethouse.R
import com.fdi.pad.pethouse.entities.Ad
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.android.synthetic.main.activity_crear_anuncio.*

class AdCreate : AppCompatActivity() {

    private val adExtra = "ADEXTRA"

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
        name = crearAdNombre.text.toString()
        url = crearAdUrl.text.toString()

        if(name.equals("") || url.equals("")){
            return
        }else{

            userSession = authentication!!.currentUser
            val ad = Ad(userSession!!.uid,name, url)
            database!!.child(databaseAds).push().setValue(ad)

            val data = Intent()
            data.putExtra(adExtra, ad)
            setResult(Activity.RESULT_OK, data)
            finish()
        }
    }
}
