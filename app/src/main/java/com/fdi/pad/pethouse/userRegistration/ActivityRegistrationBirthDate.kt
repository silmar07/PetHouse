package com.fdi.pad.pethouse.userRegistration

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import com.fdi.pad.pethouse.R
import com.fdi.pad.pethouse.activity_login
import com.fdi.pad.pethouse.entities.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.storage.*
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import kotlinx.android.synthetic.main.activity_registration_birthdate.*

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
    private val surnameUser  = "surname"
    /**
     * Parámetro para determinar el correo electrónico del usuario.
     */
    private val emailUser = "email"
    /**
     * Parámetro para determinar la contraseña del usuario.
     */
    private val passwordUser = "password"

    private val tag = "Register"
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
     * Autencicador de la aplicación dado por la tecnología FireBase.
     */
    private var authentication: FirebaseAuth? = null
    private var database: DatabaseReference? = null
    private var storage: StorageReference? = null
    private var dateSetListener: DatePickerDialog.OnDateSetListener? = null

    private var calendar: Calendar? = null

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

        buttonNextRegistrationBirthdate.setOnClickListener { nextButton() }
        buttonDateRegistrationBirthdate.setOnClickListener { datePicker() }

        var dataFormater = SimpleDateFormat("dd/MM/yyyy", Locale.US)
        textViewBirthdateRegistrationDate.text = dataFormater.format(System.currentTimeMillis())

        dateSetListener = DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->

            calendar?.set(Calendar.YEAR, year)
            calendar?.set(Calendar.MONTH, monthOfYear)
            calendar?.set(Calendar.DAY_OF_MONTH, dayOfMonth)

            yearBirthDate = calendar!!.get(Calendar.YEAR)
            monthBirthDate = calendar!!.get(Calendar.MONTH)
            dayBirthDate = calendar!!.get(Calendar.DAY_OF_MONTH)

            val date = "$dayBirthDate/$monthBirthDate/$yearBirthDate"

            textViewBirthdateRegistrationDate.text = date
        }

        /*Recibimos los datos del intent.*/
        name = intent.getStringExtra(nameUser)
        surname = intent.getStringExtra(surnameUser)
        email = intent.getStringExtra(emailUser)
        password = intent.getStringExtra(passwordUser)
    }

    /*--------------------------MÉTODOS PRIVADOS---------------------------------*/
    /**
     * Comprueba que los casilleros no estén vacíos.
     *
     * @return Casilleros correctos
     */
    private fun validateForm(birthdate: String): Boolean {
        var correct = true

        when {
            TextUtils.isEmpty(birthdate) -> {
                textViewBirthdateRegistrationDate.error = "Requerido."
                correct = false
            }
            yearOld() >= 18 -> textViewBirthdateRegistrationDate.error = null
            else -> {
                textViewBirthdateRegistrationDate.error = "Requieres ser mayor de edad"
                correct = false
            }
        }
        return correct
    }

    private fun yearOld(): Int {
        var age = 0
        try {

            val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.US)

            val birthdate = dateFormat.parse("$dayBirthDate/$monthBirthDate/$yearBirthDate")

            val currentdate = calendar!!.time

            val formatter = SimpleDateFormat("yyyyMMdd", Locale.US)
            val from = Integer.parseInt(formatter.format(birthdate))
            val to = Integer.parseInt(formatter.format(currentdate))

            age = (to - from) / 10000

        } catch (e: ParseException) {
            e.printStackTrace()
        }

        return age
    }

    private fun datePicker() {
        DatePickerDialog(this@ActivityRegistrationBirthDate, dateSetListener,
                calendar!!.get(Calendar.YEAR),
                calendar!!.get(Calendar.MONTH),
                calendar!!.get(Calendar.DAY_OF_MONTH)).show()
    }

    private fun nextButton() {
        val birthdate = textViewBirthdateRegistrationDate.text.toString()
        if (!validateForm(birthdate)) {
            return
        }
        authentication!!.createUserWithEmailAndPassword(email!!, password!!)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        val user_session = authentication!!.currentUser
                        val user = User(user_session!!.uid, name!!, surname!!, email!!, birthdate)

                        database!!.child(databaseUsers).child(user_session.uid).setValue(user)
                        val intent = Intent(this@ActivityRegistrationBirthDate, activity_login::class.java)
                        startActivity(intent)
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(tag, "createUserWithEmail:failure", task.exception)
                        Toast.makeText(this@ActivityRegistrationBirthDate, "Autentificación fallida.",
                                Toast.LENGTH_SHORT).show()
                    }
                }
    }
}