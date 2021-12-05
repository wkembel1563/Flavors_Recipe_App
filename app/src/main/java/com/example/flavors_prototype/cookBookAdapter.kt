package com.example.flavors_prototype

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class cookBookAdapter (private val dataList : ArrayList<Recipe>,
                       private val recipeIngredients: ArrayList<ArrayList<Ingredient>>,
                       private val activity : Activity)
: RecyclerView.Adapter<cookBookAdapter.MyViewHolder>() {



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
        holder.instructions.text = currentitem.Instructions

        /* set up ingredient list recycler view */
        holder.dataItemRecyclerView.layoutManager = LinearLayoutManager(activity)//= LinearLayoutManager(activity)
        holder.dataItemRecyclerView.setHasFixedSize(true)

        /* pass to ingredients to ingredients list adapter */
        var adapter = CookBookIngredAdapter(recipeIngredients[position])
        holder.dataItemRecyclerView.adapter = adapter

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
        val instructions : TextView = itemView.findViewById(R.id.tvinstructions)
        var dataItemRecyclerView: RecyclerView = itemView.findViewById(R.id.cookBookIngredients)

    }



}

