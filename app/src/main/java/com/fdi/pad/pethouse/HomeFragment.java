package com.fdi.pad.pethouse;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.net.Uri;

import java.util.ArrayList;

/**
 * Created by silvi on 4/5/18.
 */

public class HomeFragment extends Fragment {

    private ListView listView;
    private ArrayList<AnuncioList> listaAnuncios;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home,null);
    }


    @Override
    public void onActivityCreated(Bundle state) {
        super.onActivityCreated(state);

        listView = (ListView)getView().findViewById(R.id.listaAnuncios);

        cargarLista();


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                AnuncioList anuncio = AnuncioList.getAnuncio(position,listaAnuncios);
                Uri s = anuncio.getUri();

                Intent intent = new Intent(Intent.ACTION_VIEW,s);
                startActivity(intent);

            }
        });
    }

    private void cargarLista() {

        //creamso la lista dodne vamso a poner los enlaces
        ArrayList<AnuncioList> lista = new ArrayList<>();

        //creamos la lista de anuncios
        cargarAnuncios();

        int i = 0;
        while( i < listaAnuncios.size()){
            lista.add(listaAnuncios.get(i));
            i++;
        }

        ArrayAdapter<AnuncioList> adapter = new ArrayAdapter<>(getActivity().getApplicationContext(),
                android.R.layout.simple_expandable_list_item_1,lista);

        listView.setAdapter(adapter);

    }

    private void cargarAnuncios() {

        listaAnuncios = new ArrayList<>();

        listaAnuncios.add(new AnuncioList("Anuncio 1","https://www.google.es/?gws_rd=ssl",0));
        listaAnuncios.add(new AnuncioList("Anuncio 2","http://www.ucm.es",1));
        listaAnuncios.add(new AnuncioList("Anuncio 3","https://www.youtube.com/?hl=es&gl=ES",2));
        listaAnuncios.add(new AnuncioList("Anuncio 4","https://github.com",3));
    }

}