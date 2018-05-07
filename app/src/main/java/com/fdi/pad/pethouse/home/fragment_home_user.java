package com.fdi.pad.pethouse.home;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.fdi.pad.pethouse.R;
import com.fdi.pad.pethouse.entities.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class fragment_home_user extends Fragment {

    private static final String TAG = "Home User";
    private TextView user_name;
    private FirebaseAuth my_authentication;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home_user,null);
    }
    @Override
    public void onActivityCreated(Bundle state) {
        super.onActivityCreated(state);
        user_name = (TextView) getView().findViewById(R.id.textViewHomeUserName);

        my_authentication = FirebaseAuth.getInstance();
        FirebaseDatabase.getInstance().getReference("users").child(my_authentication.getCurrentUser().getUid())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        User user =dataSnapshot.getValue(User.class);
                        updateUI(user);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.e(TAG, databaseError.toString());
                    }
                });
    }

    private void updateUI(User user){
        user_name.setText(user.getName() + " " + user.getSurname());
    }
}
