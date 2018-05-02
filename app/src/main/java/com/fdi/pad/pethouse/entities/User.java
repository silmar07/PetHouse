package com.fdi.pad.pethouse.entities;

import org.json.JSONObject;

public class User {
    private String email;
    private String password;
    private String name;
    private String surname;
    private String birth_date;
    private String profile_picture;
    private String address;
    private String registration_date;

    public User(String email, String password, String name, String surname, String birth_date, String profile_picture, String address, String registration_date){
        this.email = email;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.birth_date = birth_date;
        this.profile_picture = profile_picture;
        this.address = address;
        this.registration_date = registration_date;
    }

    public User(String json){
        try {
            JSONObject jObject = new JSONObject(json);
            this.email = jObject.getString("email");
            this.password = jObject.getString("password");
            this.name = jObject.getString("name");
            this.surname = jObject.getString("surname");
            this.birth_date = jObject.getString("birth_date");
            this.profile_picture = jObject.getString("profile_picture");
            this.address = jObject.getString("address");
            this.registration_date = jObject.getString("registration_date");


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*--------------------------GETTERS & SETTERS--------------------------------------*/

    /*------------------EMAIL---------------------------*/
    public String getEmail() { return this.email; }
    public void setEmail(String email) { this.email = email; }

    /*------------------PASSWORD---------------------------*/
    public String getPassword() { return this.password; }
    public void setPassword(String password) { this.password = password; }

    /*------------------NAME---------------------------*/
    public String getName() { return this.name; }
    public void setName(String name) { this.name = name; }

    /*------------------SURNAME---------------------------*/
    public String getSurname() { return this.surname; }
    public void setSurname(String surname) { this.surname = surname; }

    /*------------------BIRTH DATE---------------------------*/
    public String getBirthDate() { return this.birth_date; }
    public void setBirthDate(String birth_date) { this.birth_date = birth_date; }

    /*------------------ADDRESS---------------------------*/
    public String getAddress() { return this.address; }
    public void setAddress(String address) { this.address = address; }

    /*------------------PROFILE PICTURE---------------------------*/
    public String getProfilePicture() { return this.profile_picture; }
    public void setProfilePicture(String profile_picture) { this.profile_picture = profile_picture; }

    /*------------------REGISTRATION DATE---------------------------*/
    public String getRegistrationDate() { return this.registration_date; }
    public void setRegistrationDate(String registration_date) { this.registration_date = registration_date; }
}
