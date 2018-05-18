package com.fdi.pad.pethouse.userRegistration

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import com.fdi.pad.pethouse.R
import kotlinx.android.synthetic.main.activity_registration_password.*
import java.util.regex.Pattern

/**
 * Actividad que define el paso del registro donde se introduce la contraseña del usuario.
 */
class ActivityRegistrationPassword : AppCompatActivity() {
    /*------------------------------CONSTANTES---------------------------*/
    /**
     * Parámetro para determinar el nombre del usuario.
     */
    private val nameUser = "name"
    /**
     * Parámetro para determinar los apellidos del usuario.
     */
    private val surnameUser  = "surname"
    /**
     * Parámetro para determinar el correo electrónico del usuario.
     */
    private val emailUser = "email"
    /**
     * Parámetro para determinar la contraseña del usuario.
     */
    private val passwordUser = "password"
    /**
     * Determina el patrón correcto de la contraseña del usuario.
     */
    private val patternPassword = ".{8,}"

    /*------------------------------ATRIBUTOS----------------------------*/
    /**
     * Nombre del usuario.
     */
    private var name: String? = null
    /**
     * Apellidos del usuario.
     */
    private var surname: String? = null
    /**
     * Correo electrónico del usuario.
     */
    private var email: String? = null

    /*--------------------------ETAPAS---------------------------------*/
    /**
     * Creación de la actividad.
     *
     * @param savedInstanceState El estado de la aplicación en un paquete.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration_password)

        buttonNext.setOnClickListener { buttonNext() }

        /*Recibimos los datos del intent.*/
        name = intent.getStringExtra(nameUser)
        surname = intent.getStringExtra(surnameUser)
        email = intent.getStringExtra(emailUser)
    }

    /*--------------------------MÉTODOS PRIVADOS---------------------------------*/
    /**
     * Valida el campo y envía a la siguiente pantalla.
     */
    private fun buttonNext() {
        val password = editTextPassword.text.toString()
        if (!validateForm(password)) return

        val intent = Intent(this@ActivityRegistrationPassword, ActivityRegistrationBirthDate::class.java)
        intent.putExtra(nameUser, name)
        intent.putExtra(surnameUser, surname)
        intent.putExtra(emailUser, email)
        intent.putExtra(passwordUser, password)
        startActivity(intent)
    }

    /**
     * Comprueba que el campo tenga el formato correcto.
     *
     * @param password Contraseña del usuario.
     * @return Contraseña correcta correcta
     */
    private fun validateForm(password: String): Boolean {
        var correctPassword = true

        when {
            TextUtils.isEmpty(password) -> {
                editTextPassword.error = getString(R.string.required_field)
                correctPassword = false
            }
            Pattern.matches(patternPassword, password) -> editTextPassword.error = null

            else -> {
                editTextPassword.error = getString(R.string.incorrect_password_format)
                correctPassword = false
            }
        }
        return correctPassword
    }
}