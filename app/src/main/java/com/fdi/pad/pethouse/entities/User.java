package com.fdi.pad.pethouse.entities;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class User {
    private String uid;
    private String name;
    private String surname;

    private DatabaseReference db_user = FirebaseDatabase.getInstance().getReference("users");

    public User() {};
    public User(String uid, String name, String surname){
        this.uid = uid;
        this.name = name;
        this.surname = surname;
    }
}
