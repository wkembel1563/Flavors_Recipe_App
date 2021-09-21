package com.example.flavorsv1

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.ktx.Firebase
import com.google.firebase.firestore.QueryDocumentSnapshot

import com.google.firebase.firestore.QuerySnapshot

import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.firestore.FirebaseFirestore


class MainActivity : AppCompatActivity() {
    override fun onCreate(savAedInstanceState: Bundle?) {
        val savedInstanceState
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val country1 = findViewById<Button>(R.id.country1)
        country1.setOnClickListener{
            //Print message if clicked
            Toast.makeText(this,"Some Country Clicked!",Toast.LENGTH_SHORT).show()


            val db = Firebase.firestore

            db.collection("root_collection").get()
                .addOnCompleteListener(OnCompleteListener<QuerySnapshot?> { task ->
                    if (task.isSuccessful) {
                        val list: MutableList<String> = ArrayList()
                        for (document in task.result!!) {
                            list.add(document.id)
                        }
                        Log.d(TAG, list.toString())
                    } else {
                        Log.d(TAG, "Error getting documents: ", task.exception)
                    }
                })
        }

    }

}

