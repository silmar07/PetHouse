package com.fdi.pad.pethouse.home;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.fdi.pad.pethouse.ActivityLogin;
import com.fdi.pad.pethouse.R;
import com.fdi.pad.pethouse.entities.Pet;
import com.fdi.pad.pethouse.entities.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class fragment_home_user extends Fragment {

    private static final String TAG = "Home User";
    private static final int EDIT_CODE = 1000;

    private User user;
    private TextView user_name;
    private FirebaseAuth my_authentication;
    private TextView user_birthdate;
    private TextView user_email;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_user,null);

        Button edit = view.findViewById(R.id.editar);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent;
                intent = new Intent(getActivity(),editarPerfil.class);
                intent.putExtra(editarPerfil.USER_EXTRA, user);
                startActivityForResult(intent, EDIT_CODE);
            }
        });


        Button delete = view.findViewById(R.id.borrar);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //borramso los animales asociados al usuario
                //borramso el usuario
                //Cerramso session
                FirebaseDatabase.getInstance().getReference("pets").child(my_authentication.getCurrentUser().getUid()).removeValue();
                FirebaseDatabase.getInstance().getReference("users").child(my_authentication.getCurrentUser().getUid()).removeValue();

                my_authentication.signOut();
                Intent intent = new Intent(getActivity(), ActivityLogin.class);
                startActivity(intent);

            }
        });

        return view;
    }

    public void onActivityCreated(Bundle state) {
        super.onActivityCreated(state);
        user_name = (TextView) getView().findViewById(R.id.textViewHomeUserName);
        user_birthdate = (TextView) getView().findViewById(R.id.textViewHomeUserBirthdate);
        user_email = (TextView) getView().findViewById(R.id.textViewHomeUserEmail);
        my_authentication = FirebaseAuth.getInstance();

        updateUser();
    }

    private void updateUser() {
        FirebaseDatabase.getInstance().getReference("users").child(my_authentication.getCurrentUser().getUid())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        user = dataSnapshot.getValue(User.class);
                        updateUI();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.e(TAG, databaseError.toString());
                    }
                });
    }

    private void updateUI() {
        user_name.setText(user.getName() + " " + user.getSurname());
        user_birthdate.setText(user.getBirthdate());
        user_email.setText(user.getEmail());
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == EDIT_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                user = data.getParcelableExtra(editarPerfil.USER_EXTRA);
                updateUI();
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }
}
