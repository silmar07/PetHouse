package com.fdi.pad.pethouse.home.user

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView

import com.fdi.pad.pethouse.R
import com.fdi.pad.pethouse.entities.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

@Suppress("NAME_SHADOWING")
class EditProfile : AppCompatActivity() {

    private var editName: EditText? = null
    private var editSurname: EditText? = null
    private var editBirthdate: EditText? = null
    private var editEmail: EditText? = null
    private lateinit var imagen: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editar_perfil)

        val user = intent.getParcelableExtra<User>(USER_EXTRA)

        this.editSurname = findViewById(R.id.textSurname)
        this.editName = findViewById(R.id.textNombre)
        this.editBirthdate = findViewById(R.id.textcumpleaños)
        this.editEmail = findViewById(R.id.textEmail)

        this.editBirthdate!!.setText(user.birthdate)
        this.editEmail!!.setText(user.email)
        this.editName!!.setText(user.name)
        this.editSurname!!.setText(user.surname)

        imagen = findViewById(R.id.imageView)

        val btn = findViewById<Button>(R.id.btnGuardar)
        btn.setOnClickListener {
            val database = FirebaseDatabase.getInstance().reference
            val edit = FirebaseAuth.getInstance()
            val fireU = edit.currentUser
            val user = User(fireU!!.uid, editName!!.text.toString(), editSurname!!.text.toString(), editEmail!!.text.toString(), "", editBirthdate!!.text.toString())
            database.child("users").child(fireU.uid).setValue(user)

            val data = Intent()
            data.putExtra(USER_EXTRA, user)
            setResult(Activity.RESULT_OK, data)
            finish()
        }
    }

    fun onclick() {
        cargarImagen()
    }

    private fun cargarImagen() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        intent.type = "image/"
        startActivityForResult(Intent.createChooser(intent, "Selecione la aplicacion"), 10)

    }

    override fun onActivityResult(requestCode: Int, resultCOde: Int, data: Intent) {
        super.onActivityResult(requestCode, resultCOde, data)
        if (resultCOde == Activity.RESULT_OK) {
            val path = data.data
            imagen.setImageURI(path)
        }
    }

    companion object {
        const val USER_EXTRA = "USER_EXTRA"
    }

}
