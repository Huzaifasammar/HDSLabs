package com.hds.labs.ids.hdslabs.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import com.hds.labs.ids.hdslabs.databinding.ActivitySignUpBinding
import android.widget.TextView

import android.view.ViewGroup

import android.R
import android.graphics.Color
import android.view.View
import com.hds.labs.ids.hdslabs.MainActivity


class SignUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)



        val mGender = listOf("Select Gender","Male", "Female", "Other")
        //val adapterGender = ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, mGender)
        // Initializing an ArrayAdapter
        val spinnerArrayAdapter: ArrayAdapter<String?> = object : ArrayAdapter<String?>(
            this, R.layout.simple_expandable_list_item_1, mGender
        ) {
            override fun isEnabled(position: Int): Boolean {
                return if (position == 0) {
                    // Disable the first item from Spinner
                    // First item will be use for hint
                    false
                } else {
                    true
                }
            }

            override fun getDropDownView(
                position: Int, convertView: View?, parent: ViewGroup?
            ): View? {
                val view: View = super.getDropDownView(position, convertView, parent)
                val tv:TextView = view as TextView
                if (position == 0) {
                    // Set the hint text color gray
                    tv.setTextColor(Color.GRAY)
                } else {
                    tv.setTextColor(Color.BLACK)
                }
                return view
            }
        }

        binding.etGender.setAdapter(spinnerArrayAdapter)


        binding.signUpCard.setOnClickListener {
            startActivity(Intent(this, PhoneNumberActivity::class.java))
            finish()
        }


        binding.backLink.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        binding.linearLt.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }
}