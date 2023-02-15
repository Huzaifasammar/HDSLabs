package com.hds.labs.ids.hdslabs.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.hds.labs.ids.hdslabs.databinding.ActivityPhoneNumberBinding

class PhoneNumberActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPhoneNumberBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPhoneNumberBinding.inflate(layoutInflater)
        setContentView(binding.root)



        binding.continueCard.setOnClickListener {
            startActivity(Intent(this, OtpVerifyActivity::class.java))
            finish()
        }

        binding.etNumber.setOnClickListener {
            binding.clearText.visibility = View.VISIBLE
        }


        binding.clearText.setOnClickListener {
            binding.etNumber.setText("")
            binding.clearText.visibility = View.INVISIBLE
        }

    }
}