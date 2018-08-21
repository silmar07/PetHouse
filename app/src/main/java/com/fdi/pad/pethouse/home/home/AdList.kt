package com.fdi.pad.pethouse.home.home

import android.net.Uri
import java.util.ArrayList

class AdList(private val texto: String, enlace: String, private val pos: Int) {

    val uri: Uri? = Uri.parse(enlace)
    override fun toString(): String {
        return texto
    }

    companion object {

        fun getAnuncio(pos: Int, lista: ArrayList<AdList>): AdList? {

            var a: AdList? = null
            var enc = false
            var i = 0

            while (!enc && i < lista.size) {

                val aux = lista[i]

                if (aux.pos == pos) {
                    enc = true
                    a = aux
                }
                i++
            }//while

            return a
        }
    }
}
