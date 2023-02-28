package com.hds.labs.ids.hdslabs

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.hds.labs.ids.hdslabs.Helper.LocationStreetViewHelper
import com.hds.labs.ids.hdslabs.Interface.ExitCallBackListener
import com.hds.labs.ids.hdslabs.Interface.MyLocationListener
import com.hds.labs.ids.hdslabs.Utils.*
import com.hds.labs.ids.hdslabs.activities.AttendanceActivity
import com.hds.labs.ids.hdslabs.activities.MarketingActivity
import com.hds.labs.ids.hdslabs.activities.ProfileActivity
import com.hds.labs.ids.hdslabs.activities.TargetActivity
import com.hds.labs.ids.hdslabs.databinding.ActivityMainBinding
import com.hds.labs.ids.hdslabs.model.MainItem
import com.hds.labs.ids.hdslabs.model.MainItemAdapter
import com.hds.labs.ids.hdslabs.model.UserModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import kotlin.collections.ArrayList
import kotlin.properties.Delegates


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var mItemList: ArrayList<MainItem> = ArrayList()
    private lateinit var mListAdapter: MainItemAdapter
    private lateinit var myRepositoryLocationGps: RepositoryLocationGps
    var latitude:Double=0.0
    var longitude:Double=0.0
    var checkInLatitude:Double?=null
    var checkOutLatitude:Double?=null
    var checkInLongitude:Double?=null
    var checkOutLongitude:Double?=null
    var checkInTime:String?=null
    var checkOutTime:String?=null
    var checkIn:String?=null
    private var user_id by Delegates.notNull<Int>()
    private lateinit var mPreferenceManagerClass: PreferenceManagerClass

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mPreferenceManagerClass= PreferenceManagerClass(this)
        user_id=mPreferenceManagerClass.getInteger("user_id",0)
        profile(user_id)

        ///////////////////////Location//////////////////////////////
        CoroutineScope(Dispatchers.IO).launch() {
            LocationStreetViewHelper.providerStreetViewEnabled(this@MainActivity)
            val isPermissionDone = LocationStreetViewHelper.locationStreetViewProvided(this@MainActivity)
            if (isPermissionDone) {
                withContext(Dispatchers.Main) {
                    Log.d(TAG, "onCreateView: =====isPermissionDone==$isPermissionDone")
                }
            }
        }
        getCoordinates()
        mainTopItems()
        val toggle = ActionBarDrawerToggle(
            this,
            binding.drawerLayout,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )

        binding.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        binding.toolbar.menu.setOnClickListener {
            binding.drawerLayout.openDrawer(binding.navDrawer)
        }
        binding.toolbar.setting.setOnClickListener(View.OnClickListener {
            startActivity(Intent(this,ProfileActivity::class.java))
        })






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

    override fun onBackPressed() {
        finish()
        finishAffinity()
    }
    private fun mainTopItems() {

        mItemList.add(MainItem(R.drawable.medical_report, "Attendance", 0))
        mItemList.add(MainItem(R.drawable.search_test, "Report", 1))
        mItemList.add(MainItem(R.drawable.delivery_boy, "Doctor Visit", 2))
        mItemList.add(MainItem(R.drawable.search_test, "Target", 3))


        mListAdapter = MainItemAdapter(mItemList, this@MainActivity) {
            onPositionClicks(it.pos)
        }
        binding.optionRecycler.apply {
            layoutManager = GridLayoutManager(this@MainActivity,2)
            setHasFixedSize(true)
            adapter = mListAdapter
        }

    }

    private fun onPositionClicks(position:Int) {
        when(position){
            0->{
                var intent=Intent(this,AttendanceActivity::class.java)
                intent.putExtra("check_in_time",checkInTime)
                intent.putExtra("checkin",checkIn)
                intent.putExtra("check_out_time",checkOutTime)
                intent.putExtra("check_in_latitude",checkInLatitude)
                intent.putExtra("check_out_latitude",checkOutLatitude)
                intent.putExtra("check_in_longitude",checkInLongitude)
                intent.putExtra("check_out_longitude",checkOutLongitude)
                startActivity(intent)
            }
            1->{
                Toast.makeText(applicationContext,"1",Toast.LENGTH_SHORT).show()
            }
            2->{
                startActivity(Intent(this,MarketingActivity::class.java))
            }
            3->{
                startActivity(Intent(this,TargetActivity::class.java))
            }

        }

    }

    fun getCoordinates() {
        myRepositoryLocationGps = RepositoryLocationGps(this, object : MyLocationListener {
            override fun onLocationChanged(location: Location) {
                if (location != null) {
                    latitude = location.latitude
                    longitude = location.longitude
                    val geocoder: Geocoder = Geocoder(this@MainActivity, Locale.getDefault())
                    val addresses: List<Address>? = geocoder.getFromLocation(
                        latitude,
                        longitude,
                        1
                    ) // Here 1 represent max location result to returned, by documents it recommended 1 to 5


                    binding.userLocation.text = addresses!![0].getAddressLine(0)
                    myRepositoryLocationGps.stopLocation()
                } else {
                    myRepositoryLocationGps.startLocation()
                }
            }
        })
    }
    private fun profile(id: Int) {
        Utils.showProgressDialog(this)
        val call: Call<UserModel> =
            RetrofitClient.getInstance().api().getProfile(id)
        call.enqueue(object : Callback<UserModel> {
            @SuppressLint("SetTextI18n")
            override fun onResponse(call: Call<UserModel>, response: Response<UserModel>) {
                try {
                    Log.d(TAG, "onResponse: 85" + response.body() !!)
                    if (response.body()!!.code == "200") {
                        Utils.hideProgressDialog(this@MainActivity)
                        if (response.body()?.data?.age != null) {
                            binding.userAge.text = response.body()?.data?.age + " Years"
                        }
                        if (response.body()?.data?.name != null) {
                            binding.userName.text = response.body()?.data?.name
                        }
                        if (response.body()?.data?.gender != null) {
                            binding.userGender.text = response.body()?.data?.gender
                        }
                        if (response.body()?.data?.designation != null) {
                            binding.accountNumber.text = response.body()?.data?.designation
                        }
                        if (response.body()?.data?.checkInTime != null) {
                            checkInTime = response.body()?.data?.checkInTime
                        }
                        if (response.body()?.data?.checkOutTime != null) {
                            checkOutTime = response.body()?.data?.checkOutTime
                        }
                        if (response.body()?.data?.checkInLongitude != null) {
                            checkInLongitude = response.body()?.data?.checkInLongitude
                        }
                        if (response.body()?.data?.checkOutLongitude != null) {
                            checkOutLongitude = response.body()?.data?.checkOutLongitude
                        }
                        if (response.body()?.data?.checkInLatitude != null) {
                            checkInLatitude=response.body()?.data?.checkInLatitude
                        }
                        if (response.body()?.data?.checkOutLatitude != null) {
                            checkOutLatitude=response.body()?.data?.checkOutLatitude
                        }
//                        if(response.body()?.data?.checkIn != null)
//                        {
//                            checkIn=response.body()?.data?.checkIn
//                        }
//                        else
//                        {
//                            checkIn="0"
//                        }

                    } else {
                        Utils.hideProgressDialog(this@MainActivity)
                        Toast.makeText(
                            applicationContext,
                            response.body()!!.message,
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
                catch (e:Exception)
                {
                    Utils.hideProgressDialog(this@MainActivity)
                    Log.d(TAG, "onResponse: email" + response.body()?.message)
                    Toast.makeText(
                        applicationContext,
                        e.message.toString(),
                        Toast.LENGTH_LONG
                    ).show()
                }

            }

            override fun onFailure(call: Call<UserModel>, t: Throwable) {
                Utils.hideProgressDialog(this@MainActivity)
                Log.d(TAG, "onFailure: failed==" + t.message)
                Log.d(TAG, "onFailure: failed== =" + t.localizedMessage)
                Toast.makeText(applicationContext, "failed " + t.message, Toast.LENGTH_SHORT).show()
            }
        })
    }
}