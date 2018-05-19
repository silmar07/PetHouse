package com.fdi.pad.pethouse.home

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat.checkSelfPermission
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.fdi.pad.pethouse.R
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import kotlinx.android.synthetic.main.fragment_home_search.*

/**
 *  Fragmente de la actividad donde se define el mapa de búsqueda.
 */
class FragmentHomeSearch : Fragment(), OnMapReadyCallback {
    /*------------------------------CONSTANTES---------------------------*/
    /**
     * Valor para identificar los logs del fragmento
     */
    private val valor = "MapActivity"
    /**
     * Permiso necesario para obtener la ubicación
     */
    private val fineLocation: String = Manifest.permission.ACCESS_FINE_LOCATION
    /**
     * Permiso necesario para obtener la ubicación
     */
    private val courseLocation: String = Manifest.permission.ACCESS_COARSE_LOCATION
    /**
     * Código de permiso para la obtención de ubicación
     */
    private val locationPermissionRequestCode = 1234
    /**
     * Zoom del mapa por defecto
     */
    private val defaultZoom = 15f
    /**
     * Determina si hay o no permisos para la ubicación
     */
    private var mLocationPermissionGranted = false
    /**
     * Variable de Google Maps
     */
    private var mMap: GoogleMap? = null
    /**
     * Cliente que da la ubicación del usuario
     */
    private var mFusedLocationProviderClient: FusedLocationProviderClient? = null

    /*--------------------------ETAPAS---------------------------------*/
    /**
     * Creación del fragmento.
     * @param inflater
     * @param container
     * @param savedInstanceState
     */
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_home_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getLocationPermission()
    }

    override fun onMapReady(googleMap: GoogleMap?) {
        Log.d(valor, "onMapReady: map is ready")
        mMap = googleMap

        if (mLocationPermissionGranted) {
            getDeviceLocation()

            if (checkSelfPermission(context!!, fineLocation) != PackageManager.PERMISSION_GRANTED
                    && checkSelfPermission(context!!, courseLocation) != PackageManager.PERMISSION_GRANTED) {
                return
            }
            mMap!!.isMyLocationEnabled = true
            mMap!!.uiSettings.isMyLocationButtonEnabled = true
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        Log.d(valor, "onRequestPermissionsResult: called.")
        mLocationPermissionGranted = false

        when (requestCode) {
            locationPermissionRequestCode -> {
                if (grantResults.isNotEmpty()) {
                    for (i in grantResults.indices) {
                        if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                            mLocationPermissionGranted = false
                            Log.d(valor, "onRequestPermissionsResult: permission failed")
                            return
                        }
                    }
                    Log.d(valor, "onRequestPermissionsResult: permission granted")
                    mLocationPermissionGranted = true
                    //initialize our map
                    initMap()
                }
            }
        }
    }

    /*--------------------------MÉTODOS PRIVADOS---------------------------------*/
    private fun getDeviceLocation() {
        Log.d(valor, "getDeviceLocation: getting the devices current location")

        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(activity!!)

        try {
            if (mLocationPermissionGranted) {
                mFusedLocationProviderClient!!.lastLocation.addOnCompleteListener { task ->
                    if (task.isSuccessful && task.result != null) {
                        val currentLocation = task.result

                        val latlng = LatLng(currentLocation.latitude, currentLocation.longitude)

                        moveCamera(latlng, defaultZoom)
                    } else {
                        Log.d(valor, "onComplete: current location is null")
                        Toast.makeText(activity, "unable to get current location", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        } catch (e: SecurityException) {
            Log.e(valor, "getDeviceLocation: SecurityException: " + e.message)
        }
    }

    private fun moveCamera(latLng: LatLng, zoom: Float) {
        Log.d(valor, "moveCamera: moving the camera to: lat: " + latLng.latitude + ", lng: " + latLng.longitude)
        mMap!!.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom))
    }

    private fun getLocationPermission() {
        Log.d(valor, "getLocationPermission: getting location permissions")
        val permissions = arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION)

        if (checkSelfPermission(context!!, fineLocation) == PackageManager.PERMISSION_GRANTED) {
            if (checkSelfPermission(context!!, courseLocation) == PackageManager.PERMISSION_GRANTED) {
                mLocationPermissionGranted = true
                initMap()
            } else {
                ActivityCompat.requestPermissions(activity!!,
                        permissions, locationPermissionRequestCode)
            }
        } else {
            ActivityCompat.requestPermissions(activity!!,
                    permissions,
                    locationPermissionRequestCode)
        }
    }

    private fun initMap() {
        Log.d(valor, "initMap: initializing map")
        mapView.onCreate(null)
        mapView.onResume()
        mapView.getMapAsync(this)
    }
}