package com.example.flavors_prototype

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class cookBookAdapter (private val dataList : ArrayList<Recipe>) : RecyclerView.Adapter<cookBookAdapter.MyViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        //layout inflater using the layout from data_item.xml
        val recipeView = LayoutInflater.from(parent.context).inflate(R.layout.cookbook_item,
            parent,false)
        return MyViewHolder(recipeView)
    }
    //will store data from each node in currentitem, and populates each card item with text from given place
     override fun onBindViewHolder(holder: MyViewHolder, position: Int) {



        //current position of item in array
        val currentitem = dataList[position]
        val imagePath = currentitem.image
        //Picasso.get().load(imagePath).into(holder.dishImage)

        holder.nameOfPlace.text = currentitem.Place
        holder.Recipe.text = currentitem.Recipe
        holder.prepTime.text = currentitem.PrepTime
        holder.cookTime.text = currentitem.CookTime
        holder.ingredient1.text = currentitem.Ingredient1
        holder.ingredient2.text = currentitem.Ingredient2
        holder.ingredient3.text = currentitem.Ingredient3
        holder.ingredient4.text = currentitem.Ingredient4
        holder.ingredient5.text = currentitem.Ingredient5
        holder.ingredient6.text = currentitem.Ingredient6
        holder.ingredient7.text = currentitem.Ingredient7
        holder.ingredient8.text = currentitem.Ingredient8
        holder.ingredient9.text = currentitem.Ingredient9
        holder.instructions.text = currentitem.Instructions





    }

    override fun getItemCount(): Int {

        return dataList.size
    }
    //this class binds variables to the textViews in data_item
    class MyViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){

        val nameOfPlace : TextView = itemView.findViewById(R.id.tvnameOfPlace)
        val Recipe : TextView = itemView.findViewById(R.id.tvrecipe)
        val prepTime : TextView = itemView.findViewById(R.id.tvpreptime)
        val cookTime : TextView = itemView.findViewById(R.id.tvcooktime)
        val ingredient1 : TextView = itemView.findViewById(R.id.tvingredient1)
        val ingredient2 : TextView = itemView.findViewById(R.id.tvingredient2)
        val ingredient3 : TextView = itemView.findViewById(R.id.tvingredient3)
        val ingredient4 : TextView = itemView.findViewById(R.id.tvingredient4)
        val ingredient5 : TextView = itemView.findViewById(R.id.tvingredient5)
        val ingredient6 : TextView = itemView.findViewById(R.id.tvingredient6)
        val ingredient7 : TextView = itemView.findViewById(R.id.tvingredient7)
        val ingredient8 : TextView = itemView.findViewById(R.id.tvingredient8)
        val ingredient9 : TextView = itemView.findViewById(R.id.tvingredient9)
        val instructions : TextView = itemView.findViewById(R.id.tvinstructions)
        //val  dishImage : ImageView = itemView.findViewById(R.id.cook_book_dishImage)

    }



}

