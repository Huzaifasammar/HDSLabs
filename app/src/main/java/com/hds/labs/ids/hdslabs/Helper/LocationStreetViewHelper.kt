package com.hds.labs.ids.hdslabs.Helper

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Context.LOCATION_SERVICE
import android.content.pm.PackageManager
import android.location.LocationManager
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Build
import androidx.core.app.ActivityCompat
import com.hds.labs.ids.hdslabs.Interface.ExitCallBackListener
import com.hds.labs.ids.hdslabs.Utils.EnableInternetConnectionRequestDialogueBox
import com.hds.labs.ids.hdslabs.Utils.LocationRequestDialogueBox
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.*

class LocationStreetViewHelper{

    companion object{

        suspend fun providerStreetViewEnabled(mContext: Context){
            val locationManager = mContext.getSystemService(LOCATION_SERVICE) as LocationManager
            if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
                locationStreetViewProvided(mContext)
            }else{
                withContext(Dispatchers.Main) {
                    locationStreetViewEnabledDialog(mContext)
                }
            }
        }

        private fun locationStreetViewEnabledDialog(mContext:Context) {
            val gpsEnablerDialog = LocationRequestDialogueBox(mContext,object :
                ExitCallBackListener {
                override fun onExistClick() {

                }

            })
            gpsEnablerDialog.show()
        }

        suspend fun locationStreetViewProvided(mContext: Context):Boolean {
            if (Build.VERSION.SDK_INT >= 23) {
                if (ActivityCompat.checkSelfPermission(
                        mContext,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    ) != PackageManager.PERMISSION_GRANTED
                    || ActivityCompat.checkSelfPermission(
                        mContext,
                        Manifest.permission.ACCESS_FINE_LOCATION
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    try {
                        ActivityCompat.requestPermissions(
                            mContext as Activity,
                            arrayOf(
                                Manifest.permission.ACCESS_COARSE_LOCATION,
                                Manifest.permission.ACCESS_FINE_LOCATION
                            ), 1
                        )
                    } catch (e: Exception) {
                    }

                    return false


                }else{
                    return true
                }
            }else{
                return true
            }
        }

        fun internetCheckGps(mContext: Context): Boolean{
            val internet = isInternetCTTruckAvailable(mContext)
            if (!internet){
                val mInternetCheckDialog = EnableInternetConnectionRequestDialogueBox(mContext)
                mInternetCheckDialog.show()
            }
            return internet
        }

        private fun isInternetCTTruckAvailable(mContext: Context): Boolean {
            val connectivityManager =
                mContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            var activeNetworkInfo: NetworkInfo? = null
            try {
                activeNetworkInfo = connectivityManager.activeNetworkInfo
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return activeNetworkInfo != null && activeNetworkInfo.isConnected
        }


        fun getStreetViewCTTruckCalculatedDate(dateFormat: String?, days: Int): String? {
            val cal = Calendar.getInstance()
            val s = SimpleDateFormat(dateFormat)
            cal.add(Calendar.DAY_OF_YEAR, days)
            return s.format(Date(cal.timeInMillis))
        }

    }

}