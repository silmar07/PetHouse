package com.fdi.pad.pethouse.userRegistration

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import com.fdi.pad.pethouse.R
import com.fdi.pad.pethouse.ActivityLogin
import com.fdi.pad.pethouse.entities.User
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.android.synthetic.main.activity_registration_birthdate.*
import com.google.firebase.auth.FirebaseUser
import java.text.SimpleDateFormat
import java.util.*

/**
 * Actividad que define el paso del registro donde se introduce la fecha de nacimiento del usuario.
 */
class ActivityRegistrationBirthDate : AppCompatActivity() {
    /*------------------------------CONSTANTES---------------------------*/
    /**
     * Parámetro para determinar la base de datos de los usuarios.
     */
    private val databaseUsers = "users"
    /**
     * Parámetro para determinar el nombre del usuario.
     */
    private val nameUser = "name"
    /**
     * Parámetro para determinar los apellidos del usuario.
     */
    private val surnameUser = "surname"
    /**
     * Parámetro para determinar el correo electrónico del usuario.
     */
    private val emailUser = "email"
    /**
     * Parámetro para determinar la contraseña del usuario.
     */
    private val passwordUser = "password"
    /**
     * Formato de la fecha.
     */
    private val dateFormat = "dd/MM/yyyy"

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
    /**
     * Contraseña del usuario.
     */
    private var password: String? = null

    /**
     * Año de nacimiento del usuario.
     */
    private var yearBirthDate: Int = 0
    /**
     * Mes de nacimiento del usuario.
     */
    private var monthBirthDate: Int = 0
    /**
     * Día de nacimiento del usuario.
     */
    private var dayBirthDate: Int = 0

    /**
     * Autentificador de la aplicación dado por la tecnología FireBase.
     */
    private var authentication: FirebaseAuth? = null
    /**
     * Base de datos de la aplicación dada por la tecnología FireBase.
     */
    private var database: DatabaseReference? = null
    /**
     * Almacenaje en la nube de la aplicación dada por la tecnología Firebase.
     */
    private var storage: StorageReference? = null
    /**
     * Usuario que se ha registrado.
     */
    private var userSession: FirebaseUser? = null

    /**
     * Calendario de la aplicación.
     */
    private var calendar: Calendar? = null
    /**
     * Evento que actualizará la fecha seleccionada por el DatePickerDialog
     */
    private var dateSetListener: DatePickerDialog.OnDateSetListener? = null

    /*--------------------------ETAPAS---------------------------------*/
    /**
     * Creación de la actividad.
     *
     * @param savedInstanceState El estado de la aplicación en un paquete.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration_birthdate)

        authentication = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance().reference
        storage = FirebaseStorage.getInstance().reference
        calendar = Calendar.getInstance()



        buttonNext.setOnClickListener { nextButton() }
        buttonDate.setOnClickListener { datePicker() }

        textViewDate.text = SimpleDateFormat(dateFormat, Locale.US).format(System.currentTimeMillis())

        dateSetListener = DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->

            calendar?.set(Calendar.YEAR, year)
            calendar?.set(Calendar.MONTH, monthOfYear)
            calendar?.set(Calendar.DAY_OF_MONTH, dayOfMonth)

            yearBirthDate = calendar!!.get(Calendar.YEAR)
            monthBirthDate = calendar!!.get(Calendar.MONTH) + 1
            dayBirthDate = calendar!!.get(Calendar.DAY_OF_MONTH)

            val date = "$dayBirthDate/$monthBirthDate/$yearBirthDate"
            textViewDate.text = date
        }

        /*Recibimos los datos del intent.*/
        name = intent.getStringExtra(nameUser)
        surname = intent.getStringExtra(surnameUser)
        email = intent.getStringExtra(emailUser)
        password = intent.getStringExtra(passwordUser)
    }

    /*--------------------------MÉTODOS PRIVADOS---------------------------------*/
    /**
     * Abre el calendario para seleccionar la fecha.
     */
    private fun datePicker() {
        DatePickerDialog(this@ActivityRegistrationBirthDate, dateSetListener,
                calendar!!.get(Calendar.YEAR),
                calendar!!.get(Calendar.MONTH),
                calendar!!.get(Calendar.DAY_OF_MONTH)).show()
    }

    /**
     * Ejecuta la creación del usuario.
     */
    private fun nextButton() {
        val birthdate = textViewDate.text.toString()
        if (!validateForm(birthdate)) {
            return
        }
        progressBarRegistration.visibility = View.VISIBLE

        authentication!!
                .createUserWithEmailAndPassword(email!!, password!!)
                .addOnCompleteListener(this) { task: Task<AuthResult> ->
                    if (task.isSuccessful) {
                        userSession = authentication!!.currentUser
                        val user = User(userSession!!.uid, name!!, surname!!, email!!, birthdate)
                        database!!.child(databaseUsers).child(userSession!!.uid).setValue(user)
                        registerUser()
                    } else {
                        Toast.makeText(this@ActivityRegistrationBirthDate, task.exception?.message,
                                Toast.LENGTH_SHORT).show()
                    }
                }

        // when the task is completed, make progressBar gone
    }

    /**
     * Una vez registrado el usuario, se pasa la actividad login
     */
    private fun registerUser() {
        progressBarRegistration.visibility = View.INVISIBLE

        val intent = Intent(this@ActivityRegistrationBirthDate, ActivityLogin::class.java)
        startActivity(intent)
    }

    /**
     * Comprueba que el campo tenga el formato correcto.
     *
     * @return Fecha de nacimiento correcto.
     */
    private fun validateForm(birthdate: String): Boolean {
        var correctBirthDate = true

        when {
            TextUtils.isEmpty(birthdate) -> {
                textViewBirthdate.error = getString(R.string.required_field)
                correctBirthDate = false
            }
            yearOld() >= 18 -> textViewBirthdate.error = null
            else -> {
                textViewBirthdate.error = getString(R.string.incorrect_birthdate_format)
                correctBirthDate = false
            }
        }
        return correctBirthDate
    }

    /**
     * Calcula los años del usuario.
     */
    private fun yearOld(): Int {
        val today = Calendar.getInstance()
        val birthdate = Calendar.getInstance()
        birthdate.set(Calendar.YEAR, yearBirthDate)
        birthdate.set(Calendar.MONTH, monthBirthDate)
        birthdate.set(Calendar.DAY_OF_MONTH, dayBirthDate)

        var age = today.get(Calendar.YEAR) - birthdate.get(Calendar.YEAR)
        if (birthdate.get(Calendar.MONTH) > today.get(Calendar.MONTH) ||
                birthdate.get(Calendar.MONTH) == today.get(Calendar.MONTH)
                && birthdate.get(Calendar.DATE) > today.get(Calendar.DATE)) {
            age--
        }

        return age
    }
}