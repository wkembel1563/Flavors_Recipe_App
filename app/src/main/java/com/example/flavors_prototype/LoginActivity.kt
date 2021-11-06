package com.example.flavors_prototype

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity() {

    lateinit var btnLogin: Button
    lateinit var tvSignupHere: TextView

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val etLoginEmail = findViewById<EditText>(R.id.etLoginEmail)
        var etLoginPassword = findViewById<EditText>(R.id.etLoginPassword)

        btnLogin = findViewById(R.id.btnLogin)
        tvSignupHere = findViewById(R.id.tvSignUpHere)

        auth = Firebase.auth

        btnLogin.setOnClickListener(View.OnClickListener {
            loginUser(etLoginEmail!!.text.toString(), etLoginPassword!!.text.toString())
        })

        tvSignupHere.setOnClickListener(View.OnClickListener {
            startActivity(Intent(this, SignupActivity::class.java))

        })
    }

    public override fun onStart() {
        super.onStart()
        //Check for User login
        val currentUser = auth.currentUser
        if(currentUser != null){
            startActivity(Intent(this@LoginActivity, CountryActivity::class.java))
        }
    }

    private fun loginUser(email: String, password: String) {
        auth!!.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                var user = auth.currentUser
                updateUI(user)
                Toast.makeText(this@LoginActivity,"User logged in successfully",Toast.LENGTH_SHORT).show()
                startActivity(Intent(this@LoginActivity, CountryActivity::class.java))
            } else {
                Toast.makeText(this@LoginActivity,"Log in Error: " + task.exception!!.message,Toast.LENGTH_SHORT).show()
                updateUI(null)
            }
        }
    }

    private fun updateUI(user: FirebaseUser?) {

    }
}