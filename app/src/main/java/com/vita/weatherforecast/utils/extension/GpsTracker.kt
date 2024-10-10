package com.vita.weatherforecast.utils.extension

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.app.Service
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.os.IBinder
import android.provider.Settings
import android.util.Log
import androidx.core.app.ActivityCompat


open class GpsTracker(context: Context) : Service(), LocationListener {
    private val mContext: Context = context

    // flag for GPS status
    var isGPSEnabled = false

    // flag for network status
    var isNetworkEnabled = false

    // flag for GPS status
    var canGetLocation = false
    var userLocation: Location? = null
    var userLatitude = 0.0
    var userLongitude = 0.0

    // Declaring a Location Manager
    var locationManager: LocationManager? = null

    init {
        getLocation()
    }

    fun getLocation(): Location? {
        try {
            locationManager = mContext.getSystemService(LOCATION_SERVICE) as LocationManager?

            // getting GPS status
            isGPSEnabled = locationManager?.isProviderEnabled(LocationManager.GPS_PROVIDER) == true

            // getting network status
            isNetworkEnabled = locationManager?.isProviderEnabled(LocationManager.NETWORK_PROVIDER) == true
            if (!isGPSEnabled && !isNetworkEnabled) {
                // no network provider is enabled
            } else {
                canGetLocation = true
                // First get location from Network Provider
                if (isNetworkEnabled) {
                    //check the network permission
                    if (ActivityCompat.checkSelfPermission(
                            mContext,
                            Manifest.permission.ACCESS_FINE_LOCATION
                        ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                            mContext,
                            Manifest.permission.ACCESS_COARSE_LOCATION
                        ) != PackageManager.PERMISSION_GRANTED
                    ) {
                        ActivityCompat.requestPermissions(
                            mContext as Activity, arrayOf<String>(
                                Manifest.permission.ACCESS_FINE_LOCATION,
                                Manifest.permission.ACCESS_COARSE_LOCATION
                            ), 101
                        )
                    }
                    locationManager!!.requestLocationUpdates(
                        LocationManager.NETWORK_PROVIDER,
                        MIN_TIME_BW_UPDATES,
                        MIN_DISTANCE_CHANGE_FOR_UPDATES.toFloat(), this
                    )
                    Log.d("Network", "Network")
                    if (locationManager != null) {
                        userLocation = locationManager!!
                            .getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
                        if (userLocation != null) {
                            userLatitude = userLocation?.latitude ?: 0.0
                            userLongitude = userLocation?.longitude ?: 0.0
                        }
                    }
                }

                // if GPS Enabled get lat/long using GPS Services
                if (isGPSEnabled) {
                    if (userLocation == null) {
                        //check the network permission
                        if (ActivityCompat.checkSelfPermission(
                                mContext,
                                Manifest.permission.ACCESS_FINE_LOCATION
                            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                                mContext,
                                Manifest.permission.ACCESS_COARSE_LOCATION
                            ) != PackageManager.PERMISSION_GRANTED
                        ) {
                            ActivityCompat.requestPermissions(
                                mContext as Activity, arrayOf<String>(
                                    Manifest.permission.ACCESS_FINE_LOCATION,
                                    Manifest.permission.ACCESS_COARSE_LOCATION
                                ), 101
                            )
                        }
                        locationManager!!.requestLocationUpdates(
                            LocationManager.GPS_PROVIDER,
                            MIN_TIME_BW_UPDATES,
                            MIN_DISTANCE_CHANGE_FOR_UPDATES.toFloat(), this
                        )
                        Log.d("GPS Enabled", "GPS Enabled")
                        if (locationManager != null) {
                            userLocation = locationManager!!
                                .getLastKnownLocation(LocationManager.GPS_PROVIDER)
                            if (userLocation != null) {
                                userLatitude = userLocation?.latitude ?: 0.0
                                userLongitude = userLocation?.longitude ?: 0.0
                            }
                        }
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return userLocation
    }

    /**
     * Stop using GPS listener
     * Calling this function will stop using GPS in your app
     */
    fun stopUsingGPS() {
        if (locationManager != null) {
            locationManager?.removeUpdates(this@GpsTracker)
        }
    }

    /**
     * Function to get latitude
     */
    fun getLatitude(): Double {
        if (userLocation != null) {
            userLatitude = userLocation?.latitude ?: 0.0
        }

        // return latitude
        return userLatitude
    }

    /**
     * Function to get longitude
     */
    fun getLongitude(): Double {
        if (userLocation != null) {
            userLongitude = userLocation?.longitude ?: 0.0
        }

        // return longitude
        return userLongitude
    }

    /**
     * Function to check GPS/wifi enabled
     * @return boolean
     */
    fun canGetLocation(): Boolean {
        return canGetLocation
    }

    /**
     * Function to show settings alert dialog
     * On pressing Settings button will lauch Settings Options
     */
    fun showSettingsAlert() {
        val alertDialog: AlertDialog.Builder = AlertDialog.Builder(mContext)

        // Setting Dialog Title
        alertDialog.setTitle("GPS is settings")

        // Setting Dialog Message
        alertDialog.setMessage("GPS is not enabled. Do you want to go to settings menu?")

        // On pressing Settings button
        alertDialog.setPositiveButton("Settings",
            DialogInterface.OnClickListener { dialog, which ->
                val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                mContext.startActivity(intent)
            })

        // on pressing cancel button
        alertDialog.setNegativeButton("Cancel",
            DialogInterface.OnClickListener { dialog, which -> dialog.cancel() })
        alertDialog.show()
    }

    override fun onLocationChanged(location: Location) {}
    override fun onProviderDisabled(provider: String) {}
    override fun onProviderEnabled(provider: String) {}

    @Deprecated("Deprecated in Java")
    override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {}
    override fun onBind(arg0: Intent?): IBinder? {
        return null
    }

    companion object {
        // The minimum distance to change Updates in meters
        private const val MIN_DISTANCE_CHANGE_FOR_UPDATES: Long = 10 // 10 meters

        // The minimum time between updates in milliseconds
        private const val MIN_TIME_BW_UPDATES = (1000 * 60 * 1 // 1 minute
                ).toLong()
    }
}