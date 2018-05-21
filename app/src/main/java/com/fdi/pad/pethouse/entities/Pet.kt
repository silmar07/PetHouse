package com.fdi.pad.pethouse.entities

import android.os.Parcel
import android.os.Parcelable

/**
 * Clase que representa una mascota de la aplicación.
 */
data class Pet(var name: String? = null,
               var species: String? = null,
               var breed: String? = null, //raza
               var age: String? = null,
               var stelilization: String? = null,
               var medicalData: String? = null,
               var otherData: String? = null) : Parcelable {

    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString())

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeString(species)
        parcel.writeString(breed)
        parcel.writeString(age)
        parcel.writeString(stelilization)
        parcel.writeString(medicalData)
        parcel.writeString(otherData)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Pet> {
        override fun createFromParcel(parcel: Parcel): Pet {
            return Pet(parcel)
        }

        override fun newArray(size: Int): Array<Pet?> {
            return arrayOfNulls(size)
        }
    }
}