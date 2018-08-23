package com.fdi.pad.pethouse.entities

import android.os.Parcel
import android.os.Parcelable

/**
 * Clase que representa un anuncio de la aplicación.
 */
data class Ad(var uuid: String? = null,
              var name: String? = null,
              var url: String? = null,
              var latitude: Double? = null,
              var longitude: Double? = null): Parcelable {

    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readDouble(),
            parcel.readDouble())

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(uuid)
        parcel.writeString(name)
        parcel.writeString(url)
        parcel.writeDouble(latitude!!)
        parcel.writeDouble(longitude!!)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Ad> {
        override fun createFromParcel(parcel: Parcel): Ad {
            return Ad(parcel)
        }

        override fun newArray(size: Int): Array<Ad?> {
            return arrayOfNulls(size)
        }
    }
}