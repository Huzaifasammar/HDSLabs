package com.hds.labs.ids.hdslabs.activities

import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import com.hds.labs.ids.hdslabs.MainActivity
import com.hds.labs.ids.hdslabs.Utils.PreferenceManagerClass
import com.hds.labs.ids.hdslabs.Utils.RetrofitClient
import com.hds.labs.ids.hdslabs.Utils.Utils
import com.hds.labs.ids.hdslabs.databinding.ActivityLoginBinding
import com.hds.labs.ids.hdslabs.model.UserModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    lateinit var device_token:String
    private lateinit var mPreferenceManagerClass: PreferenceManagerClass

    private val TAG = "LoginActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mPreferenceManagerClass= PreferenceManagerClass(this)
        FirebaseMessaging.getInstance().token
            .addOnCompleteListener(OnCompleteListener { task ->
                if (!task.isSuccessful) {
                    Log.w(TAG, "Fetching FCM registration token failed", task.exception)
                    return@OnCompleteListener
                }
                device_token = task.result
            })
        binding.loginCard.setOnClickListener(View.OnClickListener {
            loginUserEmailPassword()
        })

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
        binding.forgetPasswordBtn.setOnClickListener {
            val intent = Intent(this, ForgetPasswordActivity::class.java)
            startActivity(intent)
        }

    }
    private fun loginUserEmailPassword() {


       if (! validateEmail() || ! validatePassword()) {

        } else {
            Utils.showProgressDialog(this)
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()
            loginWithEmail(email,password,device_token)
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finishAffinity()
    }

    private fun loginWithEmail(email: String, password: String,device_token: String) {
        val call: Call<UserModel> =
           RetrofitClient.getInstance().api().loginUser(email, password,device_token)
        call.enqueue(object : Callback<UserModel> {
            override fun onResponse(call: Call<UserModel>, response: Response<UserModel>) {
          try {
                Log.d(TAG, "onResponse: 85" + response.body() !!)
                    if (response.body()!!.code == "200") {
                        mPreferenceManagerClass.putInteger(
                            "user_id",
                          response.body()?.data?.id
                        )
                        mPreferenceManagerClass.putString(
                            "login",
                            "1"
                        )
                        Utils.hideProgressDialog(this@LoginActivity)
                        startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                        finish()
                    } else {
                        Utils.hideProgressDialog(this@LoginActivity)
                        Log.d(TAG, "onResponse: email" + response.body() !!)
                        Toast.makeText(
                            applicationContext,
                            response.body()!!.message,
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
                catch (e:Exception)
                {
                    Utils.hideProgressDialog(this@LoginActivity)
                    Log.d(TAG, "onResponse: email" + response.body()?.message)
                    Toast.makeText(
                        applicationContext,
                        e.message.toString(),
                        Toast.LENGTH_LONG
                    ).show()
                }

            }

            override fun onFailure(call: Call<UserModel>, t: Throwable) {
                Utils.hideProgressDialog(this@LoginActivity)
                Log.d(TAG, "onFailure: failed==" + t.message)
                Log.d(TAG, "onFailure: failed== =" + t.localizedMessage)
                Toast.makeText(applicationContext, "failed " + t.message, Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun validatePassword(): Boolean {

        val password = binding.etPassword.text.toString().trim()
        return if (password.isEmpty()) {
            binding.etPassword.error = "Field can not be empty"
            false
        } else {
            binding.etPassword.error = null
            true
        }
    }


    private fun validateEmail(): Boolean {
        val email: String = binding.etEmail !!.text.toString().trim()
        return if (email.isEmpty()) {
            binding.etEmail.error = "Field can not be empty"
            false
        } else {
            binding.etEmail.error = null
            true
        }
    }



}

private fun <T> Call<T>?.enqueue(callback: Callback<UserModel>) {

}
