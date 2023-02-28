package com.hds.labs.ids.hdslabs.Interface

import android.location.Location

interface MyLocationListener {
    fun onLocationChanged(location: Location)
}