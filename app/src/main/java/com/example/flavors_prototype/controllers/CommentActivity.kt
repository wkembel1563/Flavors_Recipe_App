package com.example.flavors_prototype.controllers
import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.flavors_prototype.R
import com.example.flavors_prototype.models.Comments
import com.example.flavors_prototype.views.CommentsAdapter
import com.google.firebase.database.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.HashMap

class CommentActivity : AppCompatActivity() {

    //these string values are passed as extras from the dish view activity
    //when the comment icon is clicked in dish view activity


    //will hold all comment retrieved from specified child
    private  lateinit var  commentArrayList: ArrayList<Comments>
    //recycles view object for comments
    private  lateinit var  commentItemRecyclerView : RecyclerView

    private lateinit var commentRefs : DatabaseReference
    private lateinit var commentRef : DatabaseReference
    //reference to parent node in database


    //receives current app context in Bundle
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val dish : String = intent.extras?.get("Recipe").toString()
        val country : String = intent.extras?.get("Place").toString()
        val currentUserID = intent.extras?.get("user_id").toString()
        commentRefs = FirebaseDatabase.getInstance().getReference("kembel_test_tree").child(country).child(dish).child("Comments")
        //binds comment layout to xml file
        setContentView(R.layout.activity_comment)

        //binds recycle view for comments to its xml file
        commentItemRecyclerView = findViewById(R.id.comments_list)

        //LinearLayoutManager replaced ListView
        //assigns the type of layout manager the recycler view will use
        commentItemRecyclerView.layoutManager = LinearLayoutManager(this)

        //fixes height and width when items are added or removed
        //minimizes changes to screen view when recycler items are changed
        commentItemRecyclerView.setHasFixedSize(true)

        //this will receive and array of Comments
        commentArrayList = arrayListOf<Comments>()
        getCommentData()//calls for data, defined below


        //binds comment input to correct  portion  od xml file
        //and captures the text entered by user
        val commentInputText : EditText =findViewById(R.id.comment_input)

        //binds the post button in comment activity to correct portion of xmlfiles
        var postCommentButton : ImageButton = findViewById(R.id.post_comment_button)

        //listens for click on post button in comment activity
        postCommentButton.setOnClickListener {

            //validates comment data, defined below
            validateComment(currentUserID,commentInputText,commentRefs)

            //clears input text field after comment post button is clicked
            commentInputText.setText("")

            //override fun onCancelled(error: DatabaseError) {
           //     TODO("Not yet implemented")
           // }
        }
    }

    //will retrieve an array of comments for each recipe
    //comments will be displayed in a recycler view
    private fun getCommentData()
    {


        val dish : String = intent.extras?.get("Recipe").toString()
        val country : String = intent.extras?.get("Place").toString()

        commentRef = FirebaseDatabase.getInstance().getReference("kembel_test_tree").child(country).child(dish).child("Comments")
        //listens for changes in comments node
        //will update contents of comments when changes to node state detected
        commentRef.addValueEventListener(object : ValueEventListener {

            //main listener for data change
            override fun onDataChange(snapshot: DataSnapshot) {
                //if data exists
                if (snapshot.exists()){
                    //loop through comments in node
                    for (commentSnapshot in snapshot.children){
                        //holds comment object for each iteration
                        val commentItem = commentSnapshot.getValue(Comments::class.java)
                        //adds comments to array that will be used in recycler view
                        commentArrayList.add(commentItem!!)// !! checks that object is not null

                    }
                    //passes the array of comments to the recycler view using the
                    //CommentsAdapter view context
                    commentItemRecyclerView.adapter = CommentsAdapter(commentArrayList)
                }
            }
            //error should request fail
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }

    //this function formats and stores comments in the Comments node
    //it adds the date to the comments, adds a unique random key to every comment
    private fun validateComment(currentUserID: String, commentIpuntText : EditText, RecipeRef : DatabaseReference) {


        val commentText : String = commentIpuntText.text.toString()
        //will return toast message for user to enter text
        if(commentText.isEmpty())
        {
            //warning to user
            Toast.makeText(this, "please write something...", Toast.LENGTH_SHORT).show()
        }
        else
        {
            //gets current date
            val callDate : Calendar = Calendar.getInstance()
            //formats date
            val currentDate : SimpleDateFormat = SimpleDateFormat("dd/MM/yyyy")
            //formatted date
            val saveCurrentDate = currentDate.format(callDate.time)
            //gets current time
            val callTime : Calendar = Calendar.getInstance()
            //formats time
            val currentTime : SimpleDateFormat = SimpleDateFormat("HH:mm")
            //formatted  time
            val saveCurrentTime = currentTime.format(callTime.time)

            //generates random string that will be used as
            //unique identifier for each comment
            fun putCharTogehter(stringSize: Int): String {
                return CharArray(stringSize) {
                    ('a'..'z').random()
                }.concatToString()
            }

            //key for each comments, length of 8 characters
            var randomKey = putCharTogehter(8)

            //this is the format that will be stored in database
            //this hash map resembles the Comment model
            val commentsMap : HashMap<String, Any> = HashMap()
            commentsMap["uid"] = currentUserID
            commentsMap["comment"] = commentText
            commentsMap["date"] = saveCurrentDate
            commentsMap["time"] = saveCurrentTime

            //creates a child with random key as name and stores commentsMap
            commentRef.child(randomKey).updateChildren(commentsMap).addOnCompleteListener { p0 ->
                if (p0.isSuccessful) {//successful comment message
                    Toast.makeText(
                        this@CommentActivity,
                        "you have commented successfully...",
                        Toast.LENGTH_SHORT
                    ).show()

                } else {
                    Toast.makeText(//failed comment message
                        this@CommentActivity,
                        "Error occurred, try again...",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    //creates menu bar on top right corner of screen
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        super.onCreateOptionsMenu(menu)
        val inflater : MenuInflater = menuInflater
        with(inflater) {
            inflate(R.menu.main_menu,menu)
        }
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        super.onOptionsItemSelected(item)

        if(item.itemId == R.id.cook_book)
        {
            startActivity(Intent(this, CookBookActivity::class.java))
            //return true
        }
        if(item.itemId == R.id.country_selection)
        {
            startActivity(Intent(this, CountryActivity::class.java))
            //return true
        }


        if(item.itemId == R.id.shoppingList_selection)
        {
            startActivity(Intent(this, ShoppingListActivity::class.java))
            //return true
        }

        if(item.itemId == R.id.dish_recommendation)
        {

            startActivity(Intent(this, RecommendationActivity::class.java))
            //return true
        }

        return super.onOptionsItemSelected(item)
    }
}