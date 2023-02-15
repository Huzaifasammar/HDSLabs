package com.hds.labs.ids.hdslabs.activities

import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.hds.labs.ids.hdslabs.MainActivity
import com.hds.labs.ids.hdslabs.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)




        binding.showPassword.setOnClickListener {
            binding.etPassword.inputType = InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
            binding.showPassword.visibility = View.INVISIBLE
            binding.hidePassword.visibility = View.VISIBLE
        }

        binding.hidePassword.setOnClickListener {
            binding.etPassword.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
            binding.showPassword.visibility = View.VISIBLE
            binding.hidePassword.visibility = View.INVISIBLE
        }



        binding.signUp.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
        }

        binding.forgetPasswordBtn.setOnClickListener {
            val intent = Intent(this, ForgetPasswordActivity::class.java)
            startActivity(intent)
        }
        binding.loginCard.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
        binding.etPassword.setOnClickListener { }

    }
}