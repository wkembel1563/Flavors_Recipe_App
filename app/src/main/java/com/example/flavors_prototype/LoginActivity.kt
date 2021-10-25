package com.example.flavors_prototype

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {


    var etLoginEmail: TextInputEditText? = null
    private var etLoginPassword: TextInputEditText? = null
    lateinit var btnLogin: Button
    lateinit var tvSignupHere: TextView

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        tvSignupHere = findViewById(R.id.tvSignUpHere)


        tvSignupHere.setOnClickListener(View.OnClickListener {
            startActivity(Intent(this, SignupActivity::class.java))
        })

    }


}