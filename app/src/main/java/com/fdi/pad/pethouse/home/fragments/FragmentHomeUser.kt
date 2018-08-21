package com.fdi.pad.pethouse.home.fragments

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView

import com.fdi.pad.pethouse.R
import com.fdi.pad.pethouse.entities.User
import com.fdi.pad.pethouse.home.editarPerfil
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class FragmentHomeUser : Fragment() {

    private var user: User? = null
    private var userName: TextView? = null
    private var myAuthentication: FirebaseAuth? = null
    private var userBirthdate: TextView? = null
    private var userEmail: TextView? = null

    companion object {
        val TAG: String = FragmentHomeUser::class.java.simpleName
        fun newInstance() = FragmentHomeUser()
        const val EDIT_CODE = 1000
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_home_user, container, false)
        val edit = view.findViewById<Button>(R.id.editar)
        edit.setOnClickListener {
            val intent = Intent(activity, editarPerfil::class.java)
            intent.putExtra(editarPerfil.USER_EXTRA, user)
            startActivityForResult(intent, EDIT_CODE)
        }
        return view
    }
    
    override fun onActivityCreated(state: Bundle?) {
        super.onActivityCreated(state)
        userName = view!!.findViewById<View>(R.id.textViewHomeUserName) as TextView
        userBirthdate = view!!.findViewById<View>(R.id.textViewHomeUserBirthdate) as TextView
        userEmail = view!!.findViewById<View>(R.id.textViewHomeUserEmail) as TextView
        myAuthentication = FirebaseAuth.getInstance()

        updateUser()
    }

    private fun updateUser() {
        FirebaseDatabase.getInstance().getReference("users").child(myAuthentication!!.currentUser!!.uid)
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        user = dataSnapshot.getValue(User::class.java)
                        updateUI()
                    }

                    override fun onCancelled(databaseError: DatabaseError) {
                        Log.e(TAG, databaseError.toString())
                    }
                })
    }

    @SuppressLint("SetTextI18n")
    private fun updateUI() {
        userName!!.text = user!!.name + " " + user!!.surname
        userBirthdate!!.text = user!!.birthdate
        userEmail!!.text = user!!.email
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == EDIT_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                user = data!!.getParcelableExtra(editarPerfil.USER_EXTRA)
                updateUI()
            }
        }

        super.onActivityResult(requestCode, resultCode, data)
    }
}
