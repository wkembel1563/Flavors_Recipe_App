package com.example.flavors_prototype

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.database.DatabaseError

import com.google.firebase.database.DataSnapshot

import com.google.firebase.database.ValueEventListener
import androidx.annotation.NonNull

class DishViewActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dish)

        //val NumberOfLikes : TextView = findViewById(R.id.numberOfLikes)
        //var countLikes = 0
        val LikesRef : DatabaseReference = FirebaseDatabase.getInstance().getReference().child("Likes")
        val RatingRef : DatabaseReference = FirebaseDatabase.getInstance().getReference().child("Ratings")
        val currentUserID : String = FirebaseAuth.getInstance().currentUser?.uid.toString()

        val LikePostButton : ImageButton = findViewById(R.id.like_button)
        val CommentButton : ImageButton = findViewById(R.id.comment_button)
        val RatingBar : RatingBar = findViewById(R.id.ratingBar)
        val DelRating: Button = findViewById(R.id.delRating)

        // dish data passed from DataActivity
        val bundle : Bundle?= intent.extras

        //current user id and key used to track likes and comments
        //val currentUserID : String = FirebaseAuth.getInstance().currentUser?.uid.toString()
        val RecipeKey = bundle?.getString("dish_Recipe").toString()
        var LikeChecker : Boolean = false

        // dishView display variables
        val countryName : TextView = findViewById(R.id.dishCountryName)
        val recipe : TextView = findViewById(R.id.dishRecipe)
        val prepTime : TextView = findViewById(R.id.dishPrepTime)
        val cookTime : TextView = findViewById(R.id.dishCookTime)
        val ingredients : TextView = findViewById(R.id.dishIngredients)
        val instructions : TextView = findViewById(R.id.dishInstructions)

        ////comment and like variables
        LikePostButton.setOnClickListener {
            LikeChecker = true

            //listen for change in Likes node
            LikesRef.addValueEventListener(object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (LikeChecker.equals(true))
                    {
                        //if like already exists, remove it
                        if(snapshot.child(currentUserID).hasChild(RecipeKey))
                        {
                            LikesRef.child(currentUserID).child(RecipeKey).removeValue()
                            LikeChecker = false
                        }
                        //if no like exists, add it
                        else
                        {
                            LikesRef.child(currentUserID).child(RecipeKey).setValue(true)
                            LikeChecker = false
                        }
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            })
        }

        //Set Initial Rating and Rate Change Checker to 0
        var oldRating: Float = 0.0F
        var RateChecker: Boolean = true

        //Pull rating if any from database
        RatingRef.child(currentUserID).child(RecipeKey).get().addOnSuccessListener {
            Log.i("firebase", "Got value ${it.value}")
            //Toast.makeText(this, "The Old Rating = ${it.value}", Toast.LENGTH_SHORT).show()
            if (it.value == null) {
                oldRating = 0.0F
                RatingBar.setRating(oldRating)
                RateChecker = false
            } else {
                oldRating = " ${it.value}F".toFloat()
                RatingBar.setRating(oldRating)
                RateChecker = false
            }
        }.addOnFailureListener{
            Log.e("firebase", "Error getting data", it)
        }


        //Listen for a Change on the Rating bar
        RatingBar.setOnRatingBarChangeListener(object : RatingBar.OnRatingBarChangeListener {
            override fun onRatingChanged(p0: RatingBar?, p1: Float, p2: Boolean) {
                //Toast.makeText(this@DishViewActivity, "Given rating is: $p1", Toast.LENGTH_SHORT).show()
                RateChecker = true
                RatingRef.addValueEventListener(object : ValueEventListener{
                    override fun onDataChange(snapshot: DataSnapshot) {
                        if (RateChecker.equals(true)){
                            //If already rated, Update rating
                            RatingRef.child(currentUserID).child(RecipeKey).setValue(p1)
                            RateChecker = false
                        }
                    }
                    override fun onCancelled(error: DatabaseError) {
                        TODO("Not yet implemented")
                    }
                })
            }
        })

        //Deleting Given Rating
        DelRating.setOnClickListener{
            RatingRef.child(currentUserID).child(RecipeKey).removeValue()
            RatingBar.setRating(0.0F)
            RateChecker = false
        }

        //listen for click on comment button and start the comment activity
        //defined in fuction below
        CommentButton.setOnClickListener {

            //startActivity(context,StartComment, Bundle())//need to pass context for comment button
        }

        ///listen for click on like button
        //setLikeButtonStatus(RecipeKey)
        //listen for changes in Likes node
        LikesRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                //this will count all likes when user checks like button and sets the red heart image
                if(snapshot.child(currentUserID).hasChild(RecipeKey))
                {
                    //countLikes = snapshot.child(currentUserID).childrenCount.toInt()
                    LikePostButton.setImageResource(R.drawable.like)
                    //NumberOfLikes.setText(countLikes.toString())
                }
                //counts likes when user dislikes a dish and removes red heart imaga
                else
                {
                    //countLikes = snapshot.child(currentUserID).childrenCount.toInt()
                    LikePostButton.setImageResource(R.drawable.dislike)
                    //NumberOfLikes.setText(countLikes.toString())
                }

            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })

       //Listen for Changes in the Ratings node
//        RatingRef.addValueEventListener(object : ValueEventListener {
//            override fun onDataChange(dataSnapshot: DataSnapshot) {
//                if(dataSnapshot.child(currentUserID).hasChild(RecipeKey)){
//                    oldRating = dataSnapshot.getValue(Float::class.java)
//                    RatingBar.rating = oldRating!!
//                }else
//                {
//                    oldRating = 0.0F
//                    RatingBar.rating = oldRating!!
//                }
//            }
//
//            override fun onCancelled(databaseError: DatabaseError) {
//                println("The read failed: " + databaseError.code)
//            }
//        })


        // dishView image button
        // TODO: 11/1/21 create image for each dish. use this to manipulate
        val dishImage : ImageButton = findViewById(R.id.dishImageBtn)

        val dataCountry = bundle!!.getString("dish_Place")
        val dataRecipe = bundle.getString("dish_Recipe")
        val dataCookTime = bundle.getString("dish_CookTime")
        val dataPrepTime = bundle.getString("dish_PrepTime")
        val dataInstructions = bundle.getString("dish_Instructions")
        val dataIngredients = bundle.getString("dish_Ingredients")

        // pass dish data to UI
        countryName.text = dataCountry
        recipe.text = dataRecipe
        prepTime.text = dataPrepTime
        cookTime.text = dataCookTime
        ingredients.text = dataIngredients
        instructions.text = dataInstructions
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

private fun Button.setOnClickListener(): Boolean{
    return true
}
