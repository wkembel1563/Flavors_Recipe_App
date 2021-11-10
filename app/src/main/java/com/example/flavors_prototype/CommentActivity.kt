package com.example.flavors_prototype

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.HashMap

class CommentActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_comment)

        val country : String = intent.extras?.get("Place").toString()
        val dish : String = intent.extras?.get("Recipe").toString()
        val currentUserID = intent.extras?.get("user_id").toString()
        val RecipeRef : DatabaseReference = FirebaseDatabase.getInstance().getReference("kembel_test_tree").child(country).child(dish).child("Comments")


        val CommentList : RecyclerView = findViewById(R.id.comments_list)
        CommentList.setHasFixedSize(true)
        val linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.reverseLayout = true
        linearLayoutManager.stackFromEnd = true
        CommentList.layoutManager = linearLayoutManager

        //comment activity needs to be started here
        val commentInputText : EditText =findViewById(R.id.comment_input)
        var postCommentButton : ImageButton = findViewById(R.id.post_comment_button)

        postCommentButton.setOnClickListener {
            //in the video he uses this to retrieve the user id but it's not needed
            //RecipeRef.child(dish).addValueEventListener(object : ValueEventListener {

                //override fun onDataChange(snapshot: DataSnapshot) {
                   // if(snapshot.exists())
                   // {
                        ValidateComment(currentUserID,commentInputText,RecipeRef)
                        commentInputText.setText("")
                   // }
               // }

                //override fun onCancelled(error: DatabaseError) {
               //     TODO("Not yet implemented")
               // }

           // })
        }

    }

    private fun ValidateComment(currentUserID: String, commentIpuntText : EditText, RecipeRef : DatabaseReference) {

        val commentText : String = commentIpuntText.text.toString()
        if(commentText.isEmpty())
        {
            Toast.makeText(this, "please write something...", Toast.LENGTH_SHORT).show()

        }
        else
        {
            val callDate : Calendar = Calendar.getInstance()
            val currentDate : SimpleDateFormat = SimpleDateFormat("dd--MM--yyyy")
            val saveCurrentDate = currentDate.format(callDate.time)

            val callTime : Calendar = Calendar.getInstance()
            val currentTime : SimpleDateFormat = SimpleDateFormat("HH:mm")
            val saveCurrentTime = currentDate.format(callTime.time)

            val randomKey = currentUserID + currentDate + currentTime
            //this is the format that will be stored in database, make reference to the node you want to put in
            val commentsMap : HashMap<String, Any> = HashMap()
            commentsMap.put("uid", currentUserID)
            commentsMap.put("comment", commentText)
            commentsMap.put("date", saveCurrentDate)
            commentsMap.put("time", saveCurrentTime)

            RecipeRef.child(currentUserID).updateChildren(commentsMap).addOnCompleteListener(object : OnCompleteListener<Void>{

                override fun onComplete(p0: Task<Void>) {
                    if(p0.isSuccessful)
                    {
                        Toast.makeText(this@CommentActivity, "you have commented successfully...", Toast.LENGTH_SHORT).show()

                    }
                    else
                    {
                        Toast.makeText(this@CommentActivity, "Error occurred, try again...", Toast.LENGTH_SHORT).show()
                    }

                }
            })


        }



    }

    //creates menu bar
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        super.onCreateOptionsMenu(menu)
        val inflater : MenuInflater = menuInflater
        with(inflater) {
            inflate(R.menu.main_menu,menu)
        }
        return true
    }
    //when user selects option from menu, start that activity
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        super.onOptionsItemSelected(item)

        if(item.itemId == R.id.cook_book)
        {
            startActivity(Intent(this, cookBookActivity::class.java))
            return true
        }
        if(item.itemId == R.id.country_selection)
        {

            startActivity(Intent(this, CountryActivity::class.java))
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}