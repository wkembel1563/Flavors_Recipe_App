package com.example.flavors_prototype

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ShopListAdapter (private val dataList : ArrayList<Recipe>) : RecyclerView.Adapter<ShopListAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        //layout inflater using the layout from data_item.xml
        val shopListView= LayoutInflater.from(parent.context).inflate(R.layout.activity_shopping_list,
            parent,false)
        return MyViewHolder(shopListView)
    }
    //will store data from each node in currentitem, and populates each card item with text from given place
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {



        //current position of item in array
        val currentitem = dataList[position]


        holder.Recipe.text = currentitem.Recipe
        holder.ingredient1.text = currentitem.Ingredient1
        holder.ingredient2.text = currentitem.Ingredient2
        holder.ingredient3.text = currentitem.Ingredient3
        holder.ingredient4.text = currentitem.Ingredient4
        holder.ingredient5.text = currentitem.Ingredient5
        holder.ingredient6.text = currentitem.Ingredient6
        holder.ingredient7.text = currentitem.Ingredient7
        holder.ingredient8.text = currentitem.Ingredient8
        holder.ingredient9.text = currentitem.Ingredient9





    }

    override fun getItemCount(): Int {

        return dataList.size
    }
    //this class binds variables to the textViews in data_item
    class MyViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){


        val Recipe : TextView = itemView.findViewById(R.id.tvdishName)

        val ingredient1 : TextView = itemView.findViewById(R.id.tvingredients1)
        val ingredient2 : TextView = itemView.findViewById(R.id.tvingredients2)
        val ingredient3 : TextView = itemView.findViewById(R.id.tvingredients3)
        val ingredient4 : TextView = itemView.findViewById(R.id.tvingredients4)
        val ingredient5 : TextView = itemView.findViewById(R.id.tvingredients5)
        val ingredient6 : TextView = itemView.findViewById(R.id.tvingredients6)
        val ingredient7 : TextView = itemView.findViewById(R.id.tvingredients7)
        val ingredient8 : TextView = itemView.findViewById(R.id.tvingredients8)
        val ingredient9 : TextView = itemView.findViewById(R.id.tvingredients9)


    }

}