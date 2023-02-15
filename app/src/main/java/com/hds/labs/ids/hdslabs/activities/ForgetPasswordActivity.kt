package com.hds.labs.ids.hdslabs.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.hds.labs.ids.hdslabs.databinding.ActivityForgetPasswordBinding

class ForgetPasswordActivity : AppCompatActivity() {
    private lateinit var binding: ActivityForgetPasswordBinding



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityForgetPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.verifyEmailCard.setOnClickListener {
            val intent = Intent(this, PasswordEmailVerificationActivity::class.java)
            startActivity(intent)
        }

    }
}