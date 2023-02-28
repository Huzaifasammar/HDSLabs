package com.hds.labs.ids.hdslabs.activities

import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.provider.Settings
import com.hds.labs.ids.hdslabs.MainActivity
import com.hds.labs.ids.hdslabs.Utils.PreferenceManagerClass
import com.hds.labs.ids.hdslabs.databinding.ActivityIdsSpashScreenBinding

class SplashScreenHdsActivity : AppCompatActivity() {
    private lateinit var binding:ActivityIdsSpashScreenBinding
    private lateinit var mPreferenceManagerClass: PreferenceManagerClass


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityIdsSpashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mPreferenceManagerClass= PreferenceManagerClass(this)

        Handler().postDelayed({
            if(mPreferenceManagerClass.getString("login","0")=="1")
            {
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
            else
            {
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }

        }, 1000)

//        if(isDeveloperOptionsEnabled(this))
//        {
//            finishAffinity()
//        }
//        else
//        {
//
//        }




    }
    fun isDeveloperOptionsEnabled(context: Context): Boolean {
        return when {
            Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN -> {
                Settings.Secure.getInt(context.contentResolver,
                    Settings.Global.DEVELOPMENT_SETTINGS_ENABLED, 0) != 0
            }
            Build.VERSION.SDK_INT <= Build.VERSION_CODES.JELLY_BEAN -> {
                Settings.Secure.getInt(context.contentResolver,
                    Settings.Secure.DEVELOPMENT_SETTINGS_ENABLED, 0) != 0
            }
            else -> {
                return false
            }
        }
    }
}