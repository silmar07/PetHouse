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

/**
 * Actividad que define el paso del registro donde se introduce el email del usuario.
 */
class ActivityRegistrationEmail : AppCompatActivity() {
    /*------------------------------CONSTANTES---------------------------*/
    /**
     * Parámetro para determinar la base de datos de los usuarios.
     */
    private val databaseUsers = "users"
    /**
     * Parámetro para determinar el uuid del usuario.
     */
    private val uuidUser = "uuid"
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

        buttonNext.setOnClickListener { buttonNext() }

        /*Recibimos los datos del intent.*/
        name = intent.getStringExtra(nameUser)
        surname = intent.getStringExtra(surnameUser)
    }

    /*--------------------------MÉTODOS PRIVADOS---------------------------------*/
    /**
     * Valida el email y envía a la siguiente pantalla.
     */
    private fun buttonNext() {
        val email = editTextEmail.text.toString()

        if (!validateForm(email)) return
        else checkUser(email)
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
                editTextEmail.error = getString(R.string.required_field)
                correctEmail = false
            }

            Patterns.EMAIL_ADDRESS.matcher(email).matches() -> editTextEmail.error = null

            else -> {
                editTextEmail.error = getString(R.string.incorrect_email_format)
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
                var user: User? = null

                /*Buscamos el email entre los usuarios*/
                while (!exist && iteratorUser!!.iterator().hasNext()) {
                    val data = iteratorUser.iterator().next()
                    user = data.getValue(User::class.java)!!

                    if (user.email.equals(email)) exist = true
                }

                if (exist) existUser(user?.uuid!!)
                else noExistUser(email)
            }
        })
    }

    /**
     * Continua con la inicialización del usuario existente.
     *
     * @param uuid Identificador del usuario existente.
     */
    private fun existUser(uuid: String) {
        val intent = Intent(this, ActivityRegistrationAddress::class.java)
        intent.putExtra(uuidUser, uuid)
        startActivity(intent)
    }

    /**
     * Sigue con el registro del nuevo usuario.
     *
     * @param email Correo electrónico del usuario.
     */
    private fun noExistUser(email: String) {
        val intent = Intent(this, ActivityRegistrationPassword::class.java)
        intent.putExtra(nameUser, name)
        intent.putExtra(surnameUser, surname)
        intent.putExtra(emailUser, email)
        startActivity(intent)
    }
}