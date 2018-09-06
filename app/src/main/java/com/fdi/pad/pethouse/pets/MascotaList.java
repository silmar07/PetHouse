package com.fdi.pad.pethouse;

import android.net.Uri;

import java.util.ArrayList;

/**
 * Created by silvi on 5/5/18.
 */

public class MascotaList {


    private String nombre;
    private int pos;
    //private String[] uidsPet;
    private String uid;

    public MascotaList(String n, String id, int p){

        nombre = n;
        pos = p;
        uid = id;
    }

    public String toString() {
        return nombre;
    }

    public String getUidsPet() {
        return uid;
    }

    public void setUidsPet(String uidsPet) {
        this.uid = uidsPet;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setPos(int pos) {
        this.pos = pos;
    }

    public String getUid() {
        return uid;
    }

    public String getNombre() {
        return nombre;
    }

    public int getPos() {
        return pos;
    }

    public static MascotaList getUid(int pos, ArrayList<MascotaList> lista) {

        MascotaList a = null;
        boolean enc = false;
        int i = 0;

        while (!enc && i < lista.size()) {

            MascotaList aux = lista.get(i);

            if (aux.getPos() == pos) {
                enc = true;
                a = aux;
            }
            i++;
        }//while

        return a;
    }
}
