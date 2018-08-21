package com.fdi.pad.pethouse.home.fragments

import android.R.layout.simple_expandable_list_item_1
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.fdi.pad.pethouse.AnuncioList
import com.fdi.pad.pethouse.R
import com.fdi.pad.pethouse.crearAnuncio
import com.fdi.pad.pethouse.entities.Ad
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.fragment_home.*
import java.util.*

class FragmentHomeHome : Fragment() {

    //region ATRIBUTOS

    private var ads: Ad? = null

    private var listAnuncios:  ArrayList<AnuncioList>? = null

    private var listaAnunciosFirebase: ArrayList<Ad>? = null

    companion object {
        val TAG: String = FragmentHomeHome::class.java.simpleName
        fun newInstance() = FragmentHomeHome()
    }

    //endregion

    //region EVENTOS

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onActivityCreated(state: Bundle?) {
        super.onActivityCreated(state)

        bntFlotingAnuncio.setOnClickListener {
            val intent = Intent(activity, crearAnuncio::class.java)
            startActivity(intent)
        }

        cargarListaFirebase()

        listaAnunciosHome.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
            val anuncio = AnuncioList.getAnuncio(position, listAnuncios)
            val s = anuncio!!.uri

            val intent = Intent(Intent.ACTION_VIEW, s)
            startActivity(intent)
        }
    }

    //endregions

    //region MÉTODOS PRIVADOS

    private fun cargarListaFirebase() {

        listaAnunciosFirebase = ArrayList()

        FirebaseDatabase.getInstance().getReference("ads")
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        for (snapshot in dataSnapshot.children) {
                            ads = snapshot.getValue(Ad::class.java)
                            listaAnunciosFirebase!!.add(ads!!)
                        }
                        cargarListaAnuncios()
                    }

                    override fun onCancelled(databaseError: DatabaseError) {
                        Log.e(TAG, databaseError.toString())
                    }
                })
    }

    private fun cargarListaAnuncios() {
        listAnuncios = ArrayList()

        for((i, ad) in listaAnunciosFirebase!!.withIndex()) {
            listAnuncios!!.add(AnuncioList(ad.name, ad.url, i))
        }

        val adapter = ArrayAdapter(activity!!.applicationContext,
                simple_expandable_list_item_1, listAnuncios)

        listaAnunciosHome.adapter = adapter
    }

    //endregion

}