package com.fdi.pad.pethouse;

import android.net.Uri;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by silvi on 5/5/18.
 */

public class AnuncioList {


    private String enlace;
    private String texto;
    private Uri uri;
    private int pos;

    public AnuncioList(String text, String enlace, int p){

        texto = text;
        uri = Uri.parse(enlace);
        pos = p;
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
}
