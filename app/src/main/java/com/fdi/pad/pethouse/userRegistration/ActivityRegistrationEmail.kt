package com.fdi.pad.pethouse.userRegistration

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import android.util.Patterns
import com.fdi.pad.pethouse.R
import com.fdi.pad.pethouse.entities.User

import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_registration_email.*

const val emailUserRegistration = "email"
const val databaseUsers = "users"

/**
 * Actividad que define el paso del registro donde se introduce el email del usuario.
 */
class ActivityRegistrationEmail : AppCompatActivity() {
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
     * Base de datos de nuestra aplicación.
     */
    private var database: DatabaseReference? = null

    /*--------------------------ETAPAS---------------------------------*/
    /**
     * Creación de la actividad.
     *
     * @param savedInstanceState El estado de la aplicación en un paquete.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration_email)

        database = FirebaseDatabase.getInstance().reference

        buttonNextRegistrationEmail.setOnClickListener { buttonNext() }

        /*Recibimos los datos del intent.*/
        name = intent.getStringExtra(nameUserRegistration)
        surname = intent.getStringExtra(surnameUserRegistration)
    }

    /*--------------------------MÉTODOS PRIVADOS---------------------------------*/
    /**
     * Valida el email y envía a la siguiente pantalla.
     */
    private fun buttonNext() {
        val email = editTextEmailRegistrationEmail.text.toString()

        if (!validateForm(email)) return else checkUser(email)
    }

    /**
     * Comprueba que los campos no estén vacíos y el formato del email sea correcto.
     *
     * @param email Correo electrónico del usuario.
     * @return Formato del correo electrónico correcto.
     */
    private fun validateForm(email: String): Boolean {
        var correctEmail = true

        when {
            TextUtils.isEmpty(email) -> {
                editTextEmailRegistrationEmail.error = "Requerido."
                correctEmail = false
            }

            Patterns.EMAIL_ADDRESS.matcher(email).matches() -> editTextEmailRegistrationEmail.error = null

            else -> {
                editTextEmailRegistrationEmail.error = "Formato de correo incorrecto."
                correctEmail = false
            }
        }
        return correctEmail
    }

    /**
     * Comprobamos si el email existe en nuestra base de datos.
     *
     * @param email Correo electrónico del usuario.
     */
    private fun checkUser(email: String) {
        var exist = false
        database!!.child(databaseUsers).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError?) {}

            override fun onDataChange(p0: DataSnapshot?) {
                /*Cogemos todos los usuarios de la apliación*/
                val iteratorUser: MutableIterable<DataSnapshot>? = p0?.children

                /*Buscamos el email entre los usuarios*/
                while (iteratorUser!!.iterator().hasNext() && !exist) {
                    val data = iteratorUser.iterator().next()
                    val user = data.getValue(User::class.java)

                    if (user!!.email.equals(email)) exist = true
                }

                if (exist) existUser(email) else noExistUser(email)
            }
        })
    }

    /**
     * Continua con la inicialización del usuario existente.
     *
     * @param email Correo electrónico del usuario.
     */
    private fun existUser(email: String) {

    }

    /**
     * Sigue con el registro del nuevo usuario.
     *
     * @param email Correo electrónico del usuario.
     */
    private fun noExistUser(email: String) {
        val intent = Intent(this, activity_registration_password::class.java)
        intent.putExtra(nameUserRegistration, name)
        intent.putExtra(surnameUserRegistration, surname)
        intent.putExtra(emailUserRegistration, email)
        startActivity(intent)
    }
}