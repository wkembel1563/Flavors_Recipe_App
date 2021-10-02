package com.example.flavors_prototype

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

//this class stores the data from every CountryName node in an array
class MyAdapter(private val dataList : ArrayList<Recipe>) : RecyclerView.Adapter<MyAdapter.MyViewHolder>() {

    //
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        //layout inflater using the layout from data_item.xml
        val recipeView = LayoutInflater.from(parent.context).inflate(R.layout.data_item,
            parent,false)
        return MyViewHolder(recipeView)
    }
    //will store data from each node in currentitem, and populates each card item with text from given place
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val currentitem = dataList[position]
        //assign the text in each node from database to a text variable
        holder.nameOfPlace.text = currentitem.Place
        holder.Recipe.text = currentitem.Recipe
        holder.prepTime.text = currentitem.PrepTime
        holder.cookTime.text = currentitem.CookTime
        holder.ingredients.text = currentitem.Ingredients
        holder.instructions.text = currentitem.Instructions


    }
    //number of items in array
    override fun getItemCount(): Int {

        return dataList.size
    }
    //this class binds variables to the textViews in data_item
    class MyViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        //assign text from variables to proper displays in data_item.xml
        val nameOfPlace : TextView = itemView.findViewById(R.id.tvnameOfPlace)
        val Recipe : TextView = itemView.findViewById(R.id.tvrecipe)
        val prepTime : TextView = itemView.findViewById(R.id.tvpreptime)
        val cookTime : TextView = itemView.findViewById(R.id.tvcooktime)
        val ingredients : TextView = itemView.findViewById(R.id.tvingredients)
        val instructions : TextView = itemView.findViewById(R.id.tvinstructions)

    }

}