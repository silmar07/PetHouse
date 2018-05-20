package com.fdi.pad.pethouse;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.fdi.pad.pethouse.entities.Ad;
import com.fdi.pad.pethouse.entities.Pet;
import com.fdi.pad.pethouse.entities.User;
import com.fdi.pad.pethouse.home.editarPerfil;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

/**
 * Created by silvi on 4/5/18.
 */

public class MascotaFragment extends Fragment {

    private ListView listView;
    private ArrayList<MascotaList> listaMascotas;
    private ArrayList<String> listaUids;
    private MascotaList mascota;

    private static final String TAG = "Home Pet";
    private static final int EDIT_CODE = 1000;
    private Pet pets;
    private Pet petElegida;

    private FirebaseAuth my_authentication;
    private DatabaseReference petsDatabase;
    private ArrayList<Pet> listaMascotas_Firebase;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_mascota,null);

        return view;

    }


    @Override
    public void onActivityCreated(Bundle state) {
        super.onActivityCreated(state);

        //boton para añadir mascotas
        FloatingActionButton btnAñadir = (FloatingActionButton) getView().findViewById(R.id.bntFlotingMascota);

        btnAñadir.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(),crearMascota.class);
                startActivity(intent);

            }
        });


        listView = (ListView)getView().findViewById(R.id.listMascota);

        cargarListaFirebase();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                 mascota = MascotaList.getUid(position, listaMascotas);

                //obtenemos la mascota con su id
                FirebaseDatabase.getInstance().getReference("pets").child(my_authentication.getCurrentUser().getUid()).child(mascota.getUid())
                    .addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            petElegida = dataSnapshot.getValue(Pet.class);

                            //Cambiamos actividad
                            Intent intent;
                            intent = new Intent(getActivity(),perfilMascota.class);
                            intent.putExtra("uidPet", mascota.getUidsPet());
                            intent.putExtra(perfilMascota.PET_EXTRA, petElegida);
                            startActivityForResult(intent, EDIT_CODE);
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            Log.e(TAG, databaseError.toString());
                        }
                });

            }
        });
    }

    private void cargarListaFirebase() {

        listaMascotas_Firebase = new ArrayList<Pet>();
        listaUids = new ArrayList<String>();
        my_authentication = FirebaseAuth.getInstance();

        FirebaseDatabase.getInstance().getReference("pets").child(my_authentication.getCurrentUser().getUid())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        int i = 0;
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            listaUids.add(i,snapshot.getKey());
                            pets = snapshot.getValue(Pet.class);
                            listaMascotas_Firebase.add(pets);
                            i++;
                        }
                        cargarListaMascotas();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.e(TAG, databaseError.toString());
                    }
                });
    }

    private void cargarListaMascotas(){

        listaMascotas = new ArrayList<>();

        int i = 0;
        while( i < listaMascotas_Firebase.size()){
            listaMascotas.add(new MascotaList(listaMascotas_Firebase.get(i).getName(),listaUids.get(i),i));
            i ++;
        }

        ArrayList<MascotaList> lista = new ArrayList<>();
        i = 0;
        while( i < listaMascotas.size()){
            lista.add(listaMascotas.get(i));
            i++;
        }

        ArrayAdapter<MascotaList> adapter = new ArrayAdapter<>(getActivity().getApplicationContext(),
                android.R.layout.simple_expandable_list_item_1,lista);

        listView.setAdapter(adapter);

    }
}