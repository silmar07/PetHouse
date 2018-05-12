package com.fdi.pad.pethouse.registrationUser

import com.fdi.pad.pethouse.R

import kotlinx.android.synthetic.main.activity_registration_name.*

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils

const val intentName = "name"
const val intentSurname = "surname"
/**
 * Actividad que define el paso del registro donde se introduce el nombre del usuario.
 */
class ActivityRegistrationName : AppCompatActivity() {

    /*--------------------------ETAPAS---------------------------------*/
    /**
     * Creación de la actividad.
     *
     * @param savedInstanceState El estado de la aplicación en un paquete.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration_name)

        buttonNextRegistrationName.setOnClickListener { buttonNext() }
    }

    /*--------------------------MÉTODOS PRIVADOS---------------------------------*/
    /**
     * Valida los campos y envía a la siguiente pantalla.
     */
    private fun buttonNext() {
        /*Cogemos los valores de los casilleros.*/
        val name = editTextNameRegistrationName.text.toString()
        val surname = editTextSurnameRegistrationName.text.toString()
        if (!validateForm(name, surname)) {
            return
        }
        val intent = Intent(this, activity_registration_email::class.java)
        intent.putExtra(intentName, name)
        intent.putExtra(intentSurname, surname)
        startActivity(intent)
    }
    /**
     * Comprueba que los campos de nombre y apellidos no estén vacíos.
     *
     * @return Casilleros correctos
     */
    private fun validateForm(name: String, surname: String): Boolean {
        var correctName = true
        var correctSurname = true

        if (TextUtils.isEmpty(name)) {
            editTextNameRegistrationName.error = "Requerido."
            correctName = false
        }
        else editTextNameRegistrationName.error = null

        if (TextUtils.isEmpty(surname)) {
            editTextSurnameRegistrationName.error = "Requerido."
            correctSurname = false
        }
        else editTextSurnameRegistrationName.error = null

        return correctName && correctSurname
    }
}