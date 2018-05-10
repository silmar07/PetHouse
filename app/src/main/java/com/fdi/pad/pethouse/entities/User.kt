package com.fdi.pad.pethouse.entities

class User {
    var name: String? = null
    var surname: String? = null
    var birthdate: String? = null
    var address: String? = null

    constructor() {}

    constructor(name: String, surname: String, birthdate: String){
        this.name = name
        this.surname = surname
        this.birthdate = birthdate
    }

    constructor(name: String, surname: String, birthdate: String, address: String ){
        this.name = name
        this.surname = surname
        this.birthdate = birthdate
        this.address = address
    }
}