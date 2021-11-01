package com.example.flavors_prototype

import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


class DishViewActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dish)

        // dishView display variables
        val countryName : TextView = findViewById(R.id.dishCountryName)
        val recipe : TextView = findViewById(R.id.dishRecipe)
        val prepTime : TextView = findViewById(R.id.dishPrepTime)
        val cookTime : TextView = findViewById(R.id.dishCookTime)
        val ingredients : TextView = findViewById(R.id.dishIngredients)
        val instructions : TextView = findViewById(R.id.dishInstructions)
        
        // dishView image button
        // TODO: 11/1/21 create image for each dish. use this to manipulate
        val dishImage : ImageButton = findViewById(R.id.dishImageBtn)

        // dish data passed from DataActivity
        val bundle : Bundle?= intent.extras

        val dataCountry = bundle!!.getString("dish_Place")
        val dataRecipe = bundle.getString("dish_Recipe")
        val dataCookTime = bundle.getString("dish_CookTime")
        val dataPrepTime = bundle.getString("data_PrepTime")
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


}