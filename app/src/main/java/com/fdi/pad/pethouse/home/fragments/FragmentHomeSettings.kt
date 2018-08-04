package com.fdi.pad.pethouse.home.fragments

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.fdi.pad.pethouse.ActivityLogin
import com.fdi.pad.pethouse.InforApp
import com.fdi.pad.pethouse.R

import com.google.firebase.auth.FirebaseAuth


class FragmentHomeSettings : Fragment(), View.OnClickListener {

    private var button_exit: Button? = null
    private var button_information: Button? = null

    companion object {
        val TAG: String = FragmentHomeSettings::class.java.simpleName
        fun newInstance() = FragmentHomeSettings()
    }

    private var my_authentication: FirebaseAuth? = null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_ajustes, null)
    }

    override fun onActivityCreated(state: Bundle?) {
        super.onActivityCreated(state)
        button_exit = view!!.findViewById<View>(R.id.buttonExit) as Button
        button_exit!!.setOnClickListener(this)

        button_information = view!!.findViewById<View>(R.id.buttonInformation) as Button
        button_information!!.setOnClickListener(this)

        my_authentication = FirebaseAuth.getInstance()
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.buttonExit -> {
                my_authentication!!.signOut()
                val intent = Intent(activity, ActivityLogin::class.java)
                startActivity(intent)
            }
            R.id.buttonInformation -> {
                val intent = Intent(activity, InforApp::class.java)
                startActivity(intent)
            }
        }
    }
}