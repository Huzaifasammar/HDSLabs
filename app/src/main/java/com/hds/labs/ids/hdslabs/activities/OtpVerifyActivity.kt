package com.hds.labs.ids.hdslabs.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.hds.labs.ids.hdslabs.MainActivity
import com.hds.labs.ids.hdslabs.databinding.ActivityOtpVerifyBinding

class OtpVerifyActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOtpVerifyBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOtpVerifyBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.changeNumber.setOnClickListener {
            startActivity(Intent(this, PhoneNumberActivity::class.java))
            finish()
        }
        binding.resentCode.setOnClickListener {
            startActivity(Intent(this, PhoneNumberActivity::class.java))
            finish()
        }
        binding.continueCard.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

    }
}