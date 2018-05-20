package com.fdi.pad.pethouse;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.net.Uri;

import com.fdi.pad.pethouse.entities.Ad;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by silvi on 4/5/18.
 */

public class HomeFragment extends Fragment {

    private ListView listView;
    private ArrayList<AnuncioList> listaAnuncios;

    private static final String TAG = "Home Ad";
    private Ad ads;

    private FirebaseAuth my_authentication;
    private DatabaseReference adsDatabase;
    private ArrayList<Ad> listaAnuncios_Firebase;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home,null);

        return view;

    }


    @Override
    public void onActivityCreated(Bundle state) {
        super.onActivityCreated(state);

        //boton para añadir mascotas
        FloatingActionButton btnAñadir = (FloatingActionButton) getView().findViewById(R.id.bntFlotingAnuncio);

        btnAñadir.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(),crearAnuncio.class);
                startActivity(intent);

            }
        });


        listView = (ListView)getView().findViewById(R.id.listaAnuncios);

        cargarListaFirebase();


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

    private void cargarListaFirebase() {

        listaAnuncios_Firebase = new ArrayList<Ad>();

        FirebaseDatabase.getInstance().getReference("ads")
            .addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        ads = snapshot.getValue(Ad.class);
                        listaAnuncios_Firebase.add(ads);
                    }
                    cargarListaAnuncios();
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Log.e(TAG, databaseError.toString());
                }
        });
    }

    private void cargarListaAnuncios(){

        listaAnuncios = new ArrayList<>();

        int i = 0;
        while( i < listaAnuncios_Firebase.size()){
            listaAnuncios.add(new AnuncioList(listaAnuncios_Firebase.get(i).getName(),listaAnuncios_Firebase.get(i).getUrl(),i));
            i ++;
        }

        ArrayList<AnuncioList> lista = new ArrayList<>();
        i = 0;
        while( i < listaAnuncios.size()){
            lista.add(listaAnuncios.get(i));
            i++;
        }

        ArrayAdapter<AnuncioList> adapter = new ArrayAdapter<>(getActivity().getApplicationContext(),
                android.R.layout.simple_expandable_list_item_1,lista);

        listView.setAdapter(adapter);

    }
}