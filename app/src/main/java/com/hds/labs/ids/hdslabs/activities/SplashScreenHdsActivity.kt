package com.hds.labs.ids.hdslabs.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.hds.labs.ids.hdslabs.databinding.ActivityIdsSpashScreenBinding

class SplashScreenHdsActivity : AppCompatActivity() {
    private lateinit var binding:ActivityIdsSpashScreenBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityIdsSpashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Handler().postDelayed({
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }, 1000)


    }
}