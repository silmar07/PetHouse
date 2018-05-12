package com.fdi.pad.pethouse.userRegistration

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.fdi.pad.pethouse.R
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_registration_email.*

class ActivityRegistrationLogin : AppCompatActivity() {

    /*--------------------------ETAPAS---------------------------------*/
    /**
     * Creación de la actividad.
     *
     * @param savedInstanceState El estado de la aplicación en un paquete.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration_email)
        
    }
}