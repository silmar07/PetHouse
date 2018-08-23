package com.fdi.pad.pethouse.home.home

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import com.fdi.pad.pethouse.R
import com.fdi.pad.pethouse.entities.Ad
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.android.synthetic.main.activity_crear_anuncio.*

class AdCreate : AppCompatActivity() {

    private val adExtra = "ADEXTRA"

    private lateinit var locationManager: LocationManager
    private var hasGPS: Boolean = false
    private var hasNetwork: Boolean = false
    private var locationGPS: Location? = null
    private var locationNetwork: Location? = null

    //Atributos
    private var name: String? = null
    private var url: String? = null
    private var latitude: Double? = null
    private var longitude: Double? = null

    private val databaseAds = "ads"
    private var authentication: FirebaseAuth? = null
    private var database: DatabaseReference? = null
    private var storage: StorageReference? = null
    private var userSession: FirebaseUser? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crear_anuncio)

        btnCrearAdGuardar.setOnClickListener { btnGuardar() }

        authentication = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance().reference
        storage = FirebaseStorage.getInstance().reference

    }

    @SuppressLint("MissingPermission")
    private fun btnGuardar() {

        //recogemos los datos
        name = crearAdNombre.text.toString()
        url = crearAdUrl.text.toString()

        //recogemos el lugar
        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager

        hasGPS = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
        hasNetwork = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)

        if (hasGPS || hasNetwork)
        {
            if(hasGPS) {
                locationManager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER,
                    5000,
                    0F,
                    object: LocationListener{
                        override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {
                            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                        }

                        override fun onProviderEnabled(provider: String?) {
                            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                        }

                        override fun onProviderDisabled(provider: String?) {
                            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                        }

                        override fun onLocationChanged(location: Location?) {
                            if(location!=null){
                                locationGPS = location
                            }
                        }
                    })

                val localGpsLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
                if(localGpsLocation != null){
                    locationGPS = localGpsLocation
                }
            }
            if(hasNetwork) {
                locationManager.requestLocationUpdates(
                        LocationManager.NETWORK_PROVIDER,
                        5000,
                        0F,
                        object: LocationListener{
                            override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {
                                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                            }

                            override fun onProviderEnabled(provider: String?) {
                                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                            }

                            override fun onProviderDisabled(provider: String?) {
                                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                            }

                            override fun onLocationChanged(location: Location?) {
                                if(location!=null){
                                    locationNetwork = location
                                }
                            }
                        })

                val localNetworkLocation = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
                if(localNetworkLocation != null){
                    locationNetwork = localNetworkLocation
                }
                if(locationGPS != null && locationNetwork != null){
                    if(locationGPS!!.accuracy > locationNetwork!!.accuracy) {
                        latitude = locationNetwork!!.latitude
                        longitude = locationNetwork!!.longitude
                    }else{
                        latitude = locationGPS!!.latitude
                        longitude = locationGPS!!.longitude
                    }
                }

            }
        }
        else startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))

        if(name.equals("") || url.equals("")){
            return
        }else{
            userSession = authentication!!.currentUser
            val ad = Ad(userSession!!.uid,name, url, latitude, longitude)
            database!!.child(databaseAds).push().setValue(ad)

            val data = Intent()
            data.putExtra(adExtra, ad)
            setResult(Activity.RESULT_OK, data)
            finish()
        }
    }
}
