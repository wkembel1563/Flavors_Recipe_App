package com.example.flavors_prototype

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.ImageButton
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
        val currentUserID : String = FirebaseAuth.getInstance().currentUser?.uid.toString()

        val LikePostButton : ImageButton = findViewById(R.id.like_button)

        val CommentButton : ImageButton = findViewById(R.id.comment_button)

        // dish data passed from DataActivity
        val bundle : Bundle?= intent.extras

        //current user id and key used to track likes and comments
        //val currentUserID : String = FirebaseAuth.getInstance().currentUser?.uid.toString()
        val RecipeKey = bundle?.getString("dish_Recipe").toString()
        var LikeChecker : Boolean = false;
        // dishView display variables
        val countryName : TextView = findViewById(R.id.dishCountryName)
        val recipe : TextView = findViewById(R.id.dishRecipe)
        val prepTime : TextView = findViewById(R.id.dishPrepTime)
        val cookTime : TextView = findViewById(R.id.dishCookTime)
        val ingredients : TextView = findViewById(R.id.dishIngredients)
        val instructions : TextView = findViewById(R.id.dishInstructions)
        ////comment and like variables



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




        LikePostButton.setOnClickListener {

            LikeChecker = true;

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
                            LikesRef.child(currentUserID).child(RecipeKey).setValue(RecipeKey)
                            LikeChecker = false
                        }
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            })
        }
        //listen for click on comment button and start the comment activity
        //defined in fuction below
        CommentButton.setOnClickListener {


            //val dataCountry = bundle!!.getString("dish_Place")
            startActivity(Intent(this, CommentActivity::class.java).putExtra("Place", dataCountry).putExtra("Recipe", dataRecipe).putExtra("user_id",currentUserID))//need to pass context for comment button
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