package com.hds.labs.ids.hdslabs.activities

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.hds.labs.ids.hdslabs.MainActivity
import com.hds.labs.ids.hdslabs.Utils.PreferenceManagerClass
import com.hds.labs.ids.hdslabs.Utils.RetrofitClient
import com.hds.labs.ids.hdslabs.Utils.Utils
import com.hds.labs.ids.hdslabs.databinding.ActivityProfileBinding
import com.hds.labs.ids.hdslabs.model.UserModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.properties.Delegates

class ProfileActivity : AppCompatActivity() {
    lateinit var binding:ActivityProfileBinding
    private var user_id by Delegates.notNull<Int>()
    private lateinit var mPreferenceManagerClass: PreferenceManagerClass
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mPreferenceManagerClass= PreferenceManagerClass(this)
        user_id=mPreferenceManagerClass.getInteger("user_id",0)
        profile(user_id)
        binding.backarrow.setOnClickListener(View.OnClickListener {
            startActivity(Intent(this,MainActivity::class.java))
            finish()
        })
        binding.logoutBtn.setOnClickListener(View.OnClickListener {
            mPreferenceManagerClass.putString("login","0")
            startActivity(Intent(this,LoginActivity::class.java))
            finish()
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
                    Log.d(ContentValues.TAG, "onResponse: 85" + response.body() !!)
                    if (response.body()!!.code == "200") {
                        Utils.hideProgressDialog(this@ProfileActivity)

                        if (response.body()?.data?.age != "")
                        {
                            binding.userAge.text = response.body()?.data?.age + " Years"
                        }
                        if (response.body()?.data?.name != "")
                        {
                            binding.userName.text = response.body()?.data?.name
                        }
                        if (response.body()?.data?.employeeId != "")
                        {
                            binding.employeeId.text=response.body()?.data?.employeeId
                        }
                        if(response.body()?.data?.gender!="")
                        {
                            binding.userGender.text=response.body()?.data?.gender
                        }
                        if(response.body()?.data?.designation!="")
                        {
                            binding.designation.text=response.body()?.data?.designation
                        }
                        if(response.body()?.data?.email!="")
                        {
                            binding.userEmail.text=response.body()?.data?.email
                        }
                        if(response.body()?.data?.address!="")
                        {
                            binding.userLocation.text=response.body()?.data?.address
                        }
                        if(response.body()?.data?.phoneNumber!="")
                        {
                            binding.phonenumber.text=response.body()?.data?.phoneNumber
                        }
                        if(response.body()?.data?.doj!="")
                        {
                            binding.doj.text=response.body()?.data?.doj
                        }


                    } else {
                        Utils.hideProgressDialog(this@ProfileActivity)
                        Toast.makeText(
                            applicationContext,
                            response.body()?.message,
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
                catch (e:Exception)
                {
                    Utils.hideProgressDialog(this@ProfileActivity)
                    Toast.makeText(
                        applicationContext,
                        e.message.toString(),
                        Toast.LENGTH_LONG
                    ).show()
                }

            }

            override fun onFailure(call: Call<UserModel>, t: Throwable) {
                Utils.hideProgressDialog(this@ProfileActivity)
                Log.d(ContentValues.TAG, "onFailure: failed==" + t.message)
                Log.d(ContentValues.TAG, "onFailure: failed== =" + t.localizedMessage)
                Toast.makeText(applicationContext, "failed " + t.message, Toast.LENGTH_SHORT).show()
            }
        })
    }
}