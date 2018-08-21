package com.fdi.pad.pethouse.home.pet

import java.util.ArrayList

class PetList(var nombre: String?, var uidsPet: String?, var pos: Int) {

    override fun toString(): String {
        return nombre!!
    }

    fun setUid(uid: String) {
        this.uidsPet = uid
    }

    fun getUid(): String? {
        return uidsPet
    }

    companion object {

        fun getUid(pos: Int, lista: ArrayList<PetList>): PetList? {

            var a: PetList? = null
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
