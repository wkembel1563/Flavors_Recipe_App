package com.example.flavors_prototype

import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class CommentActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_comment)


        var postCommentButton : ImageButton = findViewById(R.id.post_comment_button)

        val CommentList : RecyclerView = findViewById(R.id.comments_list)

        val linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.reverseLayout = true
        linearLayoutManager.stackFromEnd = true
        CommentList.layoutManager = linearLayoutManager

        //commentInputText.findViewById<EditText>(R.id.comment_input)
        //postCommentButton.findViewById<ImageButton>(R.id.post_comment_button)
        //CommentList.findViewById<RecyclerView>(R.id.comments_list)
        CommentList.setHasFixedSize(true)
        //comment activity needs to be started here


    }
}