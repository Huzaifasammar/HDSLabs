package com.hds.labs.ids.hdslabs.activities

import android.content.ContentValues
import android.content.Intent
import android.location.Address
import android.location.Geocoder
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.hds.labs.ids.hdslabs.Helper.LocationStreetViewHelper
import com.hds.labs.ids.hdslabs.Interface.MyLocationListener
import com.hds.labs.ids.hdslabs.MainActivity
import com.hds.labs.ids.hdslabs.Utils.RepositoryLocationGps
import com.hds.labs.ids.hdslabs.databinding.ActivityMarketingBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*

class MarketingActivity : AppCompatActivity() {
    lateinit var binding:ActivityMarketingBinding
    private lateinit var myRepositoryLocationGps: RepositoryLocationGps
    var latitude:Double=0.0
    var longitude:Double=0.0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMarketingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        CoroutineScope(Dispatchers.IO).launch() {
            LocationStreetViewHelper.providerStreetViewEnabled(this@MarketingActivity)
            val isPermissionDone = LocationStreetViewHelper.locationStreetViewProvided(this@MarketingActivity)
            if (isPermissionDone) {
                withContext(Dispatchers.Main) {
                    Log.d(ContentValues.TAG, "onCreateView: =====isPermissionDone==$isPermissionDone")
                }
            }
        }
        getCoordinates()
        binding.arrowback.setOnClickListener(View.OnClickListener {
            startActivity(Intent(this,MainActivity::class.java))
            finish()
        })

    }

    fun getCoordinates() {
        myRepositoryLocationGps = RepositoryLocationGps(this, object : MyLocationListener {
            override fun onLocationChanged(location: Location) {
                if (location != null) {
                    latitude = location.latitude
                    longitude = location.longitude
                    val geocoder: Geocoder = Geocoder(this@MarketingActivity, Locale.getDefault())
                    val addresses: List<Address>? = geocoder.getFromLocation(
                        latitude,
                        longitude,
                        1
                    ) // Here 1 represent max location result to returned, by documents it recommended 1 to 5


                    binding.etAddress.setText(addresses!![0].getAddressLine(0).toString())
                    myRepositoryLocationGps.stopLocation()
                } else {
                    myRepositoryLocationGps.startLocation()
                }
            }
        })
    }
}