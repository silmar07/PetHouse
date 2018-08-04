package com.fdi.pad.pethouse.home.fragments

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import com.fdi.pad.pethouse.MascotaList
import com.fdi.pad.pethouse.R
import com.fdi.pad.pethouse.crearMascota

import com.fdi.pad.pethouse.entities.Pet
import com.fdi.pad.pethouse.perfilMascota
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

import java.util.ArrayList

/**
 * Created by silvi on 4/5/18.
 */

class FragmentHomePet : Fragment() {

    private var listView: ListView? = null
    private var listaMascotas: ArrayList<MascotaList>? = null
    private var listaUids: ArrayList<String>? = null
    private var mascota: MascotaList? = null
    private var pets: Pet? = null
    private var petElegida: Pet? = null

    private var my_authentication: FirebaseAuth? = null
    private val petsDatabase: DatabaseReference? = null
    private var listaMascotas_Firebase: ArrayList<Pet>? = null

    companion object {

        const val EDIT_CODE = 1000
        val TAG: String = FragmentHomePet::class.java.simpleName
        fun newInstance() = FragmentHomePet()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_mascota, null)

    }


    override fun onActivityCreated(state: Bundle?) {
        super.onActivityCreated(state)

        //boton para añadir mascotas
        val btnAñadir = view!!.findViewById<View>(R.id.bntFlotingMascota) as FloatingActionButton

        btnAñadir.setOnClickListener {
            val intent = Intent(activity, crearMascota::class.java)
            startActivity(intent)
        }


        listView = view!!.findViewById<View>(R.id.listMascota) as ListView

        cargarListaFirebase()

        listView!!.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
            mascota = MascotaList.getUid(position, listaMascotas)

            //obtenemos la mascota con su id
            FirebaseDatabase.getInstance().getReference("pets").child(my_authentication!!.currentUser!!.uid).child(mascota!!.uid)
                    .addListenerForSingleValueEvent(object : ValueEventListener {
                        override fun onDataChange(dataSnapshot: DataSnapshot) {
                            petElegida = dataSnapshot.getValue(Pet::class.java)

                            //Cambiamos actividad
                            val intent: Intent
                            intent = Intent(activity, perfilMascota::class.java)
                            intent.putExtra("uidPet", mascota!!.uidsPet)
                            intent.putExtra(perfilMascota.PET_EXTRA, petElegida)
                            startActivityForResult(intent, EDIT_CODE)
                        }

                        override fun onCancelled(databaseError: DatabaseError) {
                            Log.e(TAG, databaseError.toString())
                        }
                    })
        }
    }

    private fun cargarListaFirebase() {

        listaMascotas_Firebase = ArrayList()
        listaUids = ArrayList()
        my_authentication = FirebaseAuth.getInstance()

        FirebaseDatabase.getInstance().getReference("pets").child(my_authentication!!.currentUser!!.uid)
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        var i = 0
                        for (snapshot in dataSnapshot.children) {
                            listaUids!!.add(i, snapshot.key)
                            pets = snapshot.getValue(Pet::class.java)
                            listaMascotas_Firebase!!.add(pets!!)
                            i++
                        }
                        cargarListaMascotas()
                    }

                    override fun onCancelled(databaseError: DatabaseError) {
                        Log.e(TAG, databaseError.toString())
                    }
                })
    }

    private fun cargarListaMascotas() {

        listaMascotas = ArrayList()

        var i = 0
        while (i < listaMascotas_Firebase!!.size) {
            listaMascotas!!.add(MascotaList(listaMascotas_Firebase!![i].name, listaUids!![i], i))
            i++
        }

        val lista = ArrayList<MascotaList>()
        i = 0
        while (i < listaMascotas!!.size) {
            lista.add(listaMascotas!![i])
            i++
        }

        val adapter = ArrayAdapter(activity!!.applicationContext,
                android.R.layout.simple_expandable_list_item_1, lista)

        listView!!.adapter = adapter

    }
}