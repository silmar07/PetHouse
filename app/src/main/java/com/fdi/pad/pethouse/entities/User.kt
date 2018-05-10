package com.fdi.pad.pethouse.entities

class User {
    var name: String = ""
    var surname: String = ""
    var birthdate: String = ""

    constructor() {}

    constructor(name: String, surname: String, birthdate: String){
        this.name = name
        this.surname = surname
        this.birthdate = birthdate
    }
}