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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class DishViewActivity : AppCompatActivity(){

    private lateinit var ingredientList : ArrayList<Ingredient>
    private lateinit var dataItemRecyclerView : RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dish)

        ingredientList = arrayListOf<Ingredient>()

        dataItemRecyclerView = findViewById(R.id.dishIngredientRecyclerView)
        dataItemRecyclerView.layoutManager = LinearLayoutManager(this)
        dataItemRecyclerView.setHasFixedSize(true)

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

//***********************************************************Shopping List***************************************************//


        /*All of the buttons can be but in an array an then set an event lister for the children in a loop*/
        /*There is a listener for each button, it works but the code can probably be condensed with method mentioned above*/

        val dishImage : ImageButton = findViewById(R.id.dishImageBtn)
        val dataCountry = bundle!!.getString("dish_Place")
        val dataRecipe = bundle.getString("dish_Recipe")
        val dataCookTime = bundle.getString("dish_CookTime")
        val dataPrepTime = bundle.getString("dish_PrepTime")
        val dataInstructions = bundle.getString("dish_Instructions")

        /* Access ingredients node directly */
        val IngredRef : DatabaseReference =
            FirebaseDatabase.getInstance().getReference()
                .child("kembel_test_tree")
                .child(dataCountry.toString())
                .child(dataRecipe.toString())
                .child("Ingredients")

        /* Store ingredients in array list to be displayed in DishView recycler view */
        IngredRef.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()){
                   for (ingredient in snapshot.children) {
                       val dataItem = ingredient.getValue(Ingredient::class.java)
                       if (dataItem != null) {
                           ingredientList.add(dataItem)
                       }
                   }

                    /* Create recycler view for dish ingredients */
                    var adapter = DishIngredAdapter(ingredientList)
                    dataItemRecyclerView.adapter = adapter

                    /* Save Ingredients to shopping list by clicking 'Save' */
                    adapter.setOnItemClickListener(object : DishIngredAdapter.OnItemClickListener{
                        override fun onSaveClick(position: Int) {
                            val recipe : String = dataRecipe.toString()
                            val ingredientName = ingredientList[position].name.toString()
                            val url = ingredientList[position].url.toString()
                            val quantity = ingredientList[position].quantity.toString()

                            /* Write the name to recipe node's ingredient node */
                            ShoppingRef
                                .child(currentUserID)
                                .child(recipe)
                                .child(ingredientName)
                                .child("name").setValue(ingredientName)

                            /* Write the image url to recipe node's ingredient node */
                            ShoppingRef
                                .child(currentUserID)
                                .child(recipe)
                                .child(ingredientName)
                                .child("url").setValue(url)

                            /* Write the quantity to recipe node's ingredient node */
                            ShoppingRef
                                .child(currentUserID)
                                .child(recipe)
                                .child(ingredientName)
                                .child("quantity").setValue(quantity)
                        }
                    })

                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })

//        SaveShoppingList1.setOnClickListener{
//            //add ingredients to list
//            if (bundle != null) {/////instead of Recipekey the ingredient node needs to be passed here
//                if (dataRecipe != null) {
//                    ShoppingRef.child(currentUserID).child(dataRecipe).child("Ingredient1").setValue(bundle.getString("dish_Ingredient1"))
//                    //somewhere in here, the name of the dish name needs to be pushed onto database at same level as ingredients
//                    ShoppingRef.child(currentUserID).child(dataRecipe).child("Recipe").setValue(dataRecipe)
//                    //ShoppingRef.child(currentUserID).child(dataRecipe).child(dataCountry.toString()).setValue(dataCountry)
//                }
//
//            }
//
//        }
//        SaveShoppingList2.setOnClickListener{
//            //add ingredients to list
//            if (bundle != null) {/////instead of Recipekey the ingredient node needs to be passed here
//                if (dataRecipe != null) {
//                    ShoppingRef.child(currentUserID).child(dataRecipe).child("Ingredient2").setValue(bundle.getString("dish_Ingredient2"))
//                    ShoppingRef.child(currentUserID).child(dataRecipe).child("Recipe").setValue(dataRecipe)
//
//                }
//            }
//
//        }
//************************************************************************************************************************//
        // dishView image button
        // TODO: 11/1/21 create image for each dish. use this to manipulate


        // pass dish data to UI
        countryName.text = dataCountry
        recipe.text = dataRecipe
        prepTime.text = dataPrepTime
        cookTime.text = dataCookTime
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
