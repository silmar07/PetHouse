package com.fdi.pad.pethouse;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.content.Intent;

/**
 * Created by silvi on 4/5/18.
 */

public class AjustesFragment extends Fragment {

    private Button infor, cerrar;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_ajustes,null);
    }

    @Override
    public void onActivityCreated(Bundle state) {
        super.onActivityCreated(state);

/*
        //eventos de los textView
        infor = (Button) getView().findViewById(R.id.btnInfor);
        cerrar = (Button) getView().findViewById(R.id.btnCerrarSesion);

        infor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(),InforApp.class);
                startActivity(intent);

            }
        });

        cerrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        */
    }

}