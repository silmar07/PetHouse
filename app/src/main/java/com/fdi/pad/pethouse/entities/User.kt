package com.fdi.pad.pethouse.entities

/**
 * Clase que representa un usuario de la aplicación.
 */
class User {
    /**
     * Uuid del usuario.
     */
    var uuid: String? = null
    /**
     * Nombre del usuario.
     */
    var name: String? = null
    /**
     * Apellidos del usuario.
     */
    var surname: String? = null
    /**
     * Fecha de nacimiento del usuario.
     */
    var birthdate: String? = null
    /**
     * Correo electrónico del usuario.
     */
    var email: String? = null

    /**
     * Contructor vacío de la clase.
     */
    @Suppress("unused")
    constructor()

    /**
     * Constructor de la clase.
     *
     * @param uuid Uuid del usuario.
     * @param name Nombre del usuario.
     * @param surname Apellidos del usuario.
     * @param birthdate Fecha de nacimiento del usuario.
     * @param email Correo electrónico del usuario.
     */
    constructor(uuid: String, name: String, surname: String, birthdate: String, email: String){
        this.uuid = uuid
        this.name = name
        this.surname = surname
        this.birthdate = birthdate
        this.email = email
    }
}