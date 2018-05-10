package com.fdi.pad.pethouse;

import android.content.Context;
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
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by silvi on 4/5/18.
 */

public class MascotaFragment extends Fragment {

    private ListView listView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_mascota,null);
    }


    @Override
    public void onActivityCreated(Bundle state) {
        super.onActivityCreated(state);

        listView = (ListView)getView().findViewById(R.id.listMas);

        //cremaos la lista
        ArrayList<String> collection = new ArrayList<String>();

        //añadimos elementos
        collection.add("Mascota 1");
        collection.add("Mascota 2");
        collection.add("Mascota 3");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity().getApplicationContext(),
                    android.R.layout.simple_expandable_list_item_1,collection);

        listView.setAdapter(adapter);

        /**
         * el parametro posicion indica la posicion del item seleccionado en la lista
         * lo podemso utilizar para cuando pasemos datos a la otra actividad
         */
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                Intent intent = new Intent(getActivity(),perfilMascota.class);
                //pasamos el id de  las mascota
                //intent.putExtra("idMas", position);
                startActivity(intent);

            }
        });
    }
}