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
import com.fdi.pad.pethouse.AnuncioList
import com.fdi.pad.pethouse.R
import com.fdi.pad.pethouse.crearAnuncio

import com.fdi.pad.pethouse.entities.Ad
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

import java.util.ArrayList

/**
 * Created by silvi on 4/5/18.
 */

class FragmentHomeHome : Fragment() {

    private var listView: ListView? = null
    private var listaAnuncios: ArrayList<AnuncioList>? = null
    private var ads: Ad? = null

    private var listaAnuncios_Firebase: ArrayList<Ad>? = null

    companion object {
        val TAG: String = FragmentHomeHome::class.java.simpleName
        fun newInstance() = FragmentHomeHome()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_home, null)

    }


    override fun onActivityCreated(state: Bundle?) {
        super.onActivityCreated(state)

        //boton para añadir mascotas
        val btnAñadir = view!!.findViewById<View>(R.id.bntFlotingAnuncio) as FloatingActionButton

        btnAñadir.setOnClickListener {
            val intent = Intent(activity, crearAnuncio::class.java)
            startActivity(intent)
        }


        listView = view!!.findViewById<View>(R.id.listaAnuncios) as ListView

        cargarListaFirebase()


        listView!!.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
            val anuncio = AnuncioList.getAnuncio(position, listaAnuncios)
            val s = anuncio!!.uri

            val intent = Intent(Intent.ACTION_VIEW, s)
            startActivity(intent)
        }
    }

    private fun cargarListaFirebase() {

        listaAnuncios_Firebase = ArrayList()

        FirebaseDatabase.getInstance().getReference("ads")
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        for (snapshot in dataSnapshot.children) {
                            ads = snapshot.getValue(Ad::class.java)
                            listaAnuncios_Firebase!!.add(ads!!)
                        }
                        cargarListaAnuncios()
                    }

                    override fun onCancelled(databaseError: DatabaseError) {
                        Log.e(TAG, databaseError.toString())
                    }
                })
    }

    private fun cargarListaAnuncios() {

        listaAnuncios = ArrayList()

        var i = 0
        while (i < listaAnuncios_Firebase!!.size) {
            listaAnuncios!!.add(AnuncioList(listaAnuncios_Firebase!![i].name, listaAnuncios_Firebase!![i].url, i))
            i++
        }

        val lista = ArrayList<AnuncioList>()
        i = 0
        while (i < listaAnuncios!!.size) {
            lista.add(listaAnuncios!![i])
            i++
        }

        val adapter = ArrayAdapter(activity!!.applicationContext,
                android.R.layout.simple_expandable_list_item_1, lista)

        listView!!.adapter = adapter

    }
}