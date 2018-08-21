package com.fdi.pad.pethouse

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import android.util.Log
import android.util.Patterns
import android.view.View
import android.widget.Toast
import com.fdi.pad.pethouse.home.ActivityHome

import com.fdi.pad.pethouse.userRegistration.ActivityRegistrationName
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*

/**
 * Actividad que define la pantalla inicial de login.
 */
class ActivityLogin : AppCompatActivity() {

    //region ATRIBUTOS

    /**
     * Etiqueta para los logs de inicio de sesión.
     */
    private val tag = "Login"

    /**
     * Autentificador de la aplicación dado por la tecnología FireBase.
     */
    private var authentication: FirebaseAuth? = null

    //endregion

    //region EVENTOS

    /**
     * Creación de la actividad.
     *
     * @param savedInstanceState El estado de la aplicación en un paquete.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        authentication = FirebaseAuth.getInstance()

        buttonRegister.setOnClickListener { registerButton() }
        buttonEnter.setOnClickListener { enterButton() }
    }

    /**
     * La actividad comienza a ejecutarse.
     */
    public override fun onStart() {
        super.onStart()
        /*Comprobamos si hay algún usuario que ya ha iniciado la sesión.*/
        val currentUser = authentication!!.currentUser
        /*En caso de que se haya iniciado, se procede a mostrar la pantalla principal.*/
        if (currentUser != null) {
            login()
        }
    }

    /**
     * La actividad se reestablece
     */
    override fun onRestart() {
        super.onRestart()
        editTextEmail.text.clear()
        editTextPassword.text.clear()
    }

    //endregion

    //region MÉTODOS PRIVADOS

    /**
     * Procede a cambiar a la actividad "home".
     */
    private fun login() {
        progressBarLogin.visibility = View.INVISIBLE
        val intent = Intent(this@ActivityLogin, ActivityHome::class.java)
        startActivity(intent)
    }

    /**
     * Comienza el flujo del registro de un usuario.
     */
    private fun registerButton() {
        val intent = Intent(this@ActivityLogin, ActivityRegistrationName::class.java)
        startActivity(intent)
    }

    /**
     * Si los valores son válidos, loguea al usuario.
     */
    private fun enterButton() {
        /*Cogemos los valores de los casilleros.*/
        val email = editTextEmail.text.toString()
        val password = editTextPassword.text.toString()
        if (!validateForm(email, password)) {
            return
        }
        progressBarLogin.visibility = View.VISIBLE
        /*Ejecutamos la autentificación del usuario.*/
        authentication!!.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(tag, "signInWithEmail:success")
                        login()
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(tag, "signInWithEmail:failure", task.exception)
                        Toast.makeText(this@ActivityLogin, "Autentificación fallida.",
                                Toast.LENGTH_SHORT).show()
                        progressBarLogin.visibility = View.INVISIBLE
                    }
                }
    }

    /**
     * Comprueba que los casilleros no estén vacíos.
     *
     * @return Casilleros correctos
     */
    private fun validateForm(email: String, password: String): Boolean {
        var correctEmail = true
        var correctPassword = true

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

        if (TextUtils.isEmpty(password)) {
            editTextPassword.error = getString(R.string.required_field)
            correctPassword = false
        } else {
            editTextPassword.error = null
        }
        return correctEmail && correctPassword
    }

    //endregion

}

