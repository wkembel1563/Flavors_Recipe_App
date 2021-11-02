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
import android.widget.EditText
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class SignupActivity : AppCompatActivity() {

    lateinit var btnSignup: Button
    lateinit var tvLoginHere: TextView

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        val etRegEmail = findViewById<EditText>(R.id.etRegEmail)
        val etRegPassword = findViewById<EditText>(R.id.etRegPassword)
        btnSignup = findViewById(R.id.btnSignup)
        tvLoginHere = findViewById(R.id.tvLoginHere)

        auth = Firebase.auth

        btnSignup.setOnClickListener(View.OnClickListener {
             createUser(etRegEmail!!.text.toString(), etRegPassword!!.text.toString())
        })
        tvLoginHere.setOnClickListener(View.OnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        })

    }

    private fun createUser(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(OnCompleteListener<AuthResult?> { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    updateUI(user)
                    Toast.makeText(this@SignupActivity, "User registered successfully",Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this@SignupActivity, DataActivity::class.java))

                } else {
                    Toast.makeText(this@SignupActivity,"Registration Error: " + task.exception!!.message, Toast.LENGTH_SHORT).show()
                    updateUI(null)
                }
            })
    }

//    private fun sendEmailVerification(){
//        val user = auth.currentUser!!
//        user.sendEmailVerification().addOnCompleteListener(this){
//            task ->
//            //Email verification sent
//        }
//        //END Email send verification
//    }

    private fun updateUI(user: FirebaseUser?) {

    }

}