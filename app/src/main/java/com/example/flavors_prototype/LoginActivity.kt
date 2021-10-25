package com.example.flavors_prototype

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

//    var etLoginEmail: TextInputEditText? = null
//    private var etLoginPassword: TextInputEditText? = null
    lateinit var btnLogin: Button
    lateinit var tvSignupHere: TextView

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

//        etLoginEmail = findViewById(R.id.etLoginEmail)
//        etLoginPassword = findViewById(R.id.etLoginPassword)
        btnLogin = findViewById(R.id.btnLogin)
        tvSignupHere = findViewById(R.id.tvSignUpHere)

//        auth = FirebaseAuth.getInstance()

        btnLogin.setOnClickListener(View.OnClickListener {
//            loginUser()
        })

        tvSignupHere.setOnClickListener(View.OnClickListener {
            startActivity(Intent(this, SignupActivity::class.java))
        })

    }

    /*
    private fun loginUser() {
        val email = etLoginEmail!!.text.toString()
        val password = etLoginPassword!!.text.toString()
        if (TextUtils.isEmpty(email)) {
            etLoginEmail!!.error = "Email cannot be empty"
            etLoginEmail!!.requestFocus()
        } else if (TextUtils.isEmpty(password)) {
            etLoginPassword!!.error = "Password cannot be empty"
            etLoginPassword!!.requestFocus()
        } else {
            auth!!.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this@LoginActivity,"User logged in successfully",Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                } else {
                    Toast.makeText(this@LoginActivity,"Log in Error: " + task.exception!!.message,Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
    */
}