package com.hds.labs.ids.hdslabs.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.hds.labs.ids.hdslabs.databinding.ActivityPasswordEmailVerificationBinding

class PasswordEmailVerificationActivity : AppCompatActivity() {
    private lateinit var binding:ActivityPasswordEmailVerificationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPasswordEmailVerificationBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.confirmCard.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

    }
}