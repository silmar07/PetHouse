package com.fdi.pad.pethouse;

import android.net.Uri;
import android.widget.TextView;

import com.fdi.pad.pethouse.entities.Ad;

import java.util.ArrayList;

/**
 * Created by silvi on 5/5/18.
 */

public class AnuncioList {


    private String enlace;
    private String texto;
    private Uri uri;
    private int pos;
    private String uid;

    public AnuncioList(String text, String enlace, int p,String id){

        texto = text;
        uri = Uri.parse(enlace);
        pos = p;
        uid = id;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getEnlace() {
        return enlace;
    }

    public void setEnlace(String enlace) {
        this.enlace = enlace;
    }

    public String toString() {
        return texto;
    }

    public String getUid() {
        return uid;
    }

    public Uri getUri(){
        return uri;
    }

    public int getPos() {
        return pos;
    }

    public static AnuncioList getAnuncio(int pos, ArrayList<AnuncioList> lista) {

        AnuncioList a = null;
        boolean enc = false;
        int i = 0;

        while (!enc && i < lista.size()) {

            AnuncioList aux = lista.get(i);

            if (aux.getPos() == pos) {
                enc = true;
                a = aux;
            }
            i++;
        }//while

        return a;
    }

    public static AnuncioList getUid(int pos, ArrayList<AnuncioList> lista) {

        AnuncioList a = null;
        boolean enc = false;
        int i = 0;

        while (!enc && i < lista.size()) {

            AnuncioList aux = lista.get(i);

            if (aux.getPos() == pos) {
                enc = true;
                a = aux;
            }
            i++;
        }//while

        return a;
    }
}
