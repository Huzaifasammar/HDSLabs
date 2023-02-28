package com.hds.labs.ids.hdslabs.activities

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.hds.labs.ids.hdslabs.Helper.LocationStreetViewHelper
import com.hds.labs.ids.hdslabs.Interface.MyLocationListener
import com.hds.labs.ids.hdslabs.MainActivity
import com.hds.labs.ids.hdslabs.Utils.RepositoryLocationGps
import com.hds.labs.ids.hdslabs.databinding.ActivityAttendanceBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.*

class AttendanceActivity : AppCompatActivity() {
    lateinit var binding:ActivityAttendanceBinding
    var checkInLatitude:Double?=null
    var checkOutLatitude:Double?=null
    var checkInLongitude:Double?=null
    var checkOutLongitude:Double?=null
    private lateinit var myRepositoryLocationGps: RepositoryLocationGps
    var latitude:Double=0.0
    var longitude:Double=0.0
    var checkInTime:String?=null
    var checkIn:String?=null
    var checkOutTime:String?=null
    @SuppressLint("SimpleDateFormat")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityAttendanceBinding.inflate(layoutInflater)
        setContentView(binding.root)
        CoroutineScope(Dispatchers.IO).launch() {
            LocationStreetViewHelper.providerStreetViewEnabled(this@AttendanceActivity)
            val isPermissionDone = LocationStreetViewHelper.locationStreetViewProvided(this@AttendanceActivity)
            if (isPermissionDone) {
                withContext(Dispatchers.Main) {
                    Log.d(ContentValues.TAG, "onCreateView: =====isPermissionDone==$isPermissionDone")
                }
            }
        }
        getCoordinates()
        checkIn=intent.getStringExtra("checkin")
        checkInLatitude=intent.getDoubleExtra("check_in_latitude",0.0)
        checkInLongitude=intent.getDoubleExtra("check_in_longitde",0.0)
        checkOutLatitude=intent.getDoubleExtra("check_out_latitude",0.0)
        checkOutLongitude=intent.getDoubleExtra("check_out_longitude",0.0)
        checkInTime=intent.getStringExtra("check_in_time")
        checkOutTime=intent.getStringExtra("check_out_time")
        val date = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(Date())
        binding.tvdate.text= date
        binding.arrowback.setOnClickListener(View.OnClickListener {
            startActivity(Intent(this,MainActivity::class.java))
            finish()
        })
        if(checkIn=="1")
        {
            binding.btnattendance.text="Check Out"
            binding.btnattendance.setOnClickListener(View.OnClickListener {

            })
        }
        else
        {
            binding.btnattendance.text="Check In"
            binding.btnattendance.setOnClickListener(View.OnClickListener {
             checkIn="1"
            })

        }

        binding.btnattendance.setOnClickListener(View.OnClickListener {

        })
    }

    fun getCoordinates() {
        myRepositoryLocationGps = RepositoryLocationGps(this, object : MyLocationListener {
            override fun onLocationChanged(location: Location) {
                if (location != null) {
                    latitude = location.latitude
                    longitude = location.longitude
                    myRepositoryLocationGps.stopLocation()
                } else {
                    myRepositoryLocationGps.startLocation()
                }
            }
        })
    }

    private fun distance(lat1: Double, lon1: Double, lat2: Double, lon2: Double): Double {
        val theta = lon1 - lon2
        var dist = (Math.sin(deg2rad(lat1))
                * Math.sin(deg2rad(lat2))
                + (Math.cos(deg2rad(lat1))
                * Math.cos(deg2rad(lat2))
                * Math.cos(deg2rad(theta))))
        dist = Math.acos(dist)
        dist = rad2deg(dist)
        dist = dist * 60 * 1.1515 * 1609.344
        return dist
    }

    private fun deg2rad(deg: Double): Double {
        return deg * Math.PI / 180.0
    }

    private fun rad2deg(rad: Double): Double {
        return rad * 180.0 / Math.PI
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            1 -> {
                if (grantResults.isNotEmpty() && grantResults[0] ==
                    PackageManager.PERMISSION_GRANTED
                ) {
                    if ((ContextCompat.checkSelfPermission(
                            this,
                            android.Manifest.permission.ACCESS_FINE_LOCATION

                        ) ==
                                PackageManager.PERMISSION_GRANTED)
                    ) {

                        getCoordinates()
                    } else if ((ContextCompat.checkSelfPermission(
                            this,
                            android.Manifest.permission.ACCESS_COARSE_LOCATION
                        ) ==
                                PackageManager.PERMISSION_GRANTED)
                    ) {
                        getCoordinates()
                    }
                } else {
                    finishAffinity()
                }
                return
            }
        }
    }
}