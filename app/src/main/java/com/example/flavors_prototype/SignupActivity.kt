package com.example.flavors_prototype

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import android.widget.Toast

import com.google.firebase.auth.AuthResult

import com.google.android.gms.tasks.Task

import androidx.annotation.NonNull

import com.google.android.gms.tasks.OnCompleteListener

import android.text.TextUtils

class SignupActivity : AppCompatActivity() {

//    var etRegEmail: TextInputEditText? = null
//    private var etRegPassword: TextInputEditText? = null
    lateinit var btnSignup: Button
    lateinit var tvLoginHere: TextView

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

//        etRegEmail = findViewById(R.id.etRegEmail)
//        etRegPassword = findViewById(R.id.etRegPassword)
          btnSignup = findViewById(R.id.btnSignup)
          tvLoginHere = findViewById(R.id.tvLoginHere)

//        auth = FirebaseAuth.getInstance()

        btnSignup.setOnClickListener(View.OnClickListener {
//            createUser()
        })
        tvLoginHere.setOnClickListener(View.OnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        })

    }

    /*
    private fun createUser() {
        val email = etRegEmail!!.text.toString()
        val password = etRegPassword!!.text.toString()

        if (TextUtils.isEmpty(email)) {
            etRegEmail!!.error = "Email cannot be empty"
            etRegEmail!!.requestFocus()
        } else if (TextUtils.isEmpty(password)) {
            etRegPassword!!.error = "Password cannot be empty"
            etRegPassword!!.requestFocus()
        } else {
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(OnCompleteListener<AuthResult?> { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(this@SignupActivity, "User registered successfully",Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this@SignupActivity, LoginActivity::class.java))
                    } else {
                        Toast.makeText(this@SignupActivity,"Registration Error: " + task.exception!!.message, Toast.LENGTH_SHORT).show()
                    }
                })
        }
    }
    */
}