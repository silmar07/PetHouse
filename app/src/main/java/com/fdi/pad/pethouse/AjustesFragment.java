package com.fdi.pad.pethouse;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by silvi on 4/5/18.
 */

public class AjustesFragment extends Fragment implements View.OnClickListener {

    private Button button_exit;

    private FirebaseAuth my_authentication;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_ajustes,null);
    }

    @Override
    public void onActivityCreated(Bundle state) {
        super.onActivityCreated(state);
        button_exit = (Button) getView().findViewById(R.id.buttonExit);
        button_exit.setOnClickListener(this);

        my_authentication = FirebaseAuth.getInstance();
    }

    @Override
    public void onClick(View v) {
        my_authentication.signOut();
        Intent intent = new Intent(getActivity(),activity_login.class);
        startActivity(intent);
    }
}