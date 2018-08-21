package com.fdi.pad.pethouse.home.fragments

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.fdi.pad.pethouse.ActivityLogin
import com.fdi.pad.pethouse.R
import com.fdi.pad.pethouse.home.settings.InfoApp

import com.google.firebase.auth.FirebaseAuth


class FragmentHomeSettings : Fragment(), View.OnClickListener {

    private var buttonExit: Button? = null
    private var buttonInformation: Button? = null

    companion object {
        val TAG: String = FragmentHomeSettings::class.java.simpleName
        fun newInstance() = FragmentHomeSettings()
    }

    private var myAuthentication: FirebaseAuth? = null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_ajustes, container, false)
    }

    override fun onActivityCreated(state: Bundle?) {
        super.onActivityCreated(state)
        buttonExit = view!!.findViewById<View>(R.id.buttonExit) as Button
        buttonExit!!.setOnClickListener(this)

        buttonInformation = view!!.findViewById<View>(R.id.buttonInformation) as Button
        buttonInformation!!.setOnClickListener(this)

        myAuthentication = FirebaseAuth.getInstance()
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.buttonExit -> {
                myAuthentication!!.signOut()
                val intent = Intent(activity, ActivityLogin::class.java)
                startActivity(intent)
            }
            R.id.buttonInformation -> {
                val intent = Intent(activity, InfoApp::class.java)
                startActivity(intent)
            }
        }
    }
}