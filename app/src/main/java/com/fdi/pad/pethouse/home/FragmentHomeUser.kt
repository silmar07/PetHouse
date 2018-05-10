package com.fdi.pad.pethouse.home


import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import com.fdi.pad.pethouse.R
import com.fdi.pad.pethouse.entities.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class FragmentHomeUser : Fragment() {
    private var user_name: TextView? = null
    private var my_authentication: FirebaseAuth? = null
    private var user_birthdate: TextView? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.fragment_home_user, null)
    }

    override fun onActivityCreated(state: Bundle?) {
        super.onActivityCreated(state)
        user_name = view!!.findViewById<View>(R.id.textViewHomeUserName) as TextView
        user_birthdate = view!!.findViewById<View>(R.id.textViewHomeUserBirthdate) as TextView

        my_authentication = FirebaseAuth.getInstance()
        FirebaseDatabase.getInstance().getReference("users").child(my_authentication!!.currentUser!!.uid)
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        val user = dataSnapshot.getValue(User::class.java)
                        updateUI(user!!)
                    }

                    override fun onCancelled(databaseError: DatabaseError) {
                        Log.e(TAG, databaseError.toString())
                    }
                })
    }

    private fun updateUI(user: User) {
        user_name!!.text = user.name + " " + user.surname
        user_birthdate!!.text = user.birthdate
    }

    companion object {

        private val TAG = "Home User"
    }
}
