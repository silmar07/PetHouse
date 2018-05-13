package com.fdi.pad.pethouse.userRegistration

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import com.fdi.pad.pethouse.R
import kotlinx.android.synthetic.main.activity_registration_name.*

/**
 * Actividad que define el paso del registro donde se introduce el nombre del usuario.
 */
class ActivityRegistrationName : AppCompatActivity() {

    /*------------------------------CONSTANTES---------------------------*/
    /**
     * Parámetro para determinar el nombre del usuario.
     */
    private val nameUser = "name"
    /**
     * Parámetro para determinar los apellidos del usuario.
     */
    private val surnameUser  = "surname"

    /*--------------------------ETAPAS---------------------------------*/
    /**
     * Creación de la actividad.
     *
     * @param savedInstanceState El estado de la aplicación en un paquete.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration_name)

        buttonNext.setOnClickListener { buttonNext() }
    }

    /*--------------------------MÉTODOS PRIVADOS---------------------------------*/
    /**
     * Valida los campos y envía a la siguiente pantalla.
     */
    private fun buttonNext() {
        /*Cogemos los valores de los casilleros.*/
        val name = editTextName.text.toString()
        val surname = editTextSurname.text.toString()
        if (!validateForm(name, surname)) return

        val intent = Intent(this@ActivityRegistrationName, ActivityRegistrationEmail::class.java)
        intent.putExtra(nameUser, name)
        intent.putExtra(surnameUser, surname)
        startActivity(intent)
    }

    /**
     * Comprueba que los campos de nombre y apellidos no estén vacíos.
     *
     * @return Nombre y apellidos correctos
     */
    private fun validateForm(name: String, surname: String): Boolean {
        var correctName = true
        var correctSurname = true

        if (TextUtils.isEmpty(name)) {
            editTextName.error = getString(R.string.required_field)
            correctName = false
        } else editTextName.error = null

        if (TextUtils.isEmpty(surname)) {
            editTextSurname.error = getString(R.string.required_field)
            correctSurname = false
        } else editTextSurname.error = null

        return correctName && correctSurname
    }
}