package com.fdi.pad.pethouse.entities

class User {
    var name: String? = null
    var surname: String? = null
    var birthdate: String? = null
    var address: String? = null
    var email: String? = null

    @Suppress("unused")
    constructor()

    constructor(name: String, surname: String, birthdate: String, email: String){
        this.name = name
        this.surname = surname
        this.birthdate = birthdate
        this.email = email
    }

    constructor(name: String, surname: String, birthdate: String, email: String, address: String){
        this.name = name
        this.surname = surname
        this.birthdate = birthdate
        this.address = address
        this.email = email
    }
}