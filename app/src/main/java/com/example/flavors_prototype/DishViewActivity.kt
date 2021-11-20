package com.example.flavors_prototype

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Button
import android.widget.ImageButton
import android.widget.RatingBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class DishViewActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dish)

        //val NumberOfLikes : TextView = findViewById(R.id.numberOfLikes)
        //var countLikes = 0
        val LikesRef : DatabaseReference = FirebaseDatabase.getInstance().getReference().child("Likes")
        val RatingRef : DatabaseReference = FirebaseDatabase.getInstance().getReference().child("Ratings")
        val ShoppingRef : DatabaseReference = FirebaseDatabase.getInstance().getReference().child("ShoppingList")
        val currentUserID : String = FirebaseAuth.getInstance().currentUser?.uid.toString()

        val LikePostButton : ImageButton = findViewById(R.id.like_button)
        val CommentButton : ImageButton = findViewById(R.id.comment_button)
        val RatingBar : RatingBar = findViewById(R.id.ratingBar)
        val DelRating: Button = findViewById(R.id.delRating)
        val SaveShoppingList: Button = findViewById(R.id.saveToShoppingList)


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
        val ingredient1 : TextView = findViewById(R.id.dishIngredient1)
        val ingredient2 : TextView = findViewById(R.id.dishIngredient2)
        val ingredient3 : TextView = findViewById(R.id.dishIngredient3)
        val ingredient4 : TextView = findViewById(R.id.dishIngredient4)
        val ingredient5 : TextView = findViewById(R.id.dishIngredient5)
        val ingredient6 : TextView = findViewById(R.id.dishIngredient6)
        val ingredient7 : TextView = findViewById(R.id.dishIngredient7)
        val ingredient8 : TextView = findViewById(R.id.dishIngredient8)
        val ingredient9 : TextView = findViewById(R.id.dishIngredient9)
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


        //shopping list
        SaveShoppingList.setOnClickListener{
            //add ingredients to list
            if (bundle != null) {/////instead of Recipekey the ingredient node needs to be passed here
                ShoppingRef.child(currentUserID).child(RecipeKey).push().setValue(bundle.getString("dish_Ingredients"))
            }

        }

        // dishView image button
        // TODO: 11/1/21 create image for each dish. use this to manipulate
        val dishImage : ImageButton = findViewById(R.id.dishImageBtn)

        val dataCountry = bundle!!.getString("dish_Place")
        val dataRecipe = bundle.getString("dish_Recipe")
        val dataCookTime = bundle.getString("dish_CookTime")
        val dataPrepTime = bundle.getString("dish_PrepTime")
        val dataInstructions = bundle.getString("dish_Instructions")
        val dataIngredient1 = bundle.getString("dish_Ingredient1")
        val dataIngredient2 = bundle.getString("dish_Ingredient2")
        val dataIngredient3 = bundle.getString("dish_Ingredient3")
        val dataIngredient4 = bundle.getString("dish_Ingredient4")
        val dataIngredient5 = bundle.getString("dish_Ingredient5")
        val dataIngredient6 = bundle.getString("dish_Ingredient6")
        val dataIngredient7 = bundle.getString("dish_Ingredient7")
        val dataIngredient8 = bundle.getString("dish_Ingredient8")
        val dataIngredient9 = bundle.getString("dish_Ingredient9")

        // pass dish data to UI
        countryName.text = dataCountry
        recipe.text = dataRecipe
        prepTime.text = dataPrepTime
        cookTime.text = dataCookTime
        ingredient1.text = dataIngredient1
        ingredient1.text = dataIngredient2
        ingredient1.text = dataIngredient3
        ingredient1.text = dataIngredient4
        ingredient1.text = dataIngredient5
        ingredient1.text = dataIngredient6
        ingredient1.text = dataIngredient7
        ingredient1.text = dataIngredient8
        ingredient1.text = dataIngredient9
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


        if(item.itemId == R.id.shoppingList_selection)
        {
            startActivity(Intent(this, ShoppingListActivity::class.java))
            //return true
        }


        return super.onOptionsItemSelected(item)
    }
}

private fun Button.setOnClickListener(): Boolean{
    return true
}
