package com.example.flavors_prototype

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
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
            startActivity(Intent(this, cookBookActivity::class.java))
            //return true
        }
        if(item.itemId == R.id.country_selection)
        {

            startActivity(Intent(this, CountryActivity::class.java))
            //return true
        }
        return super.onOptionsItemSelected(item)
    }
}