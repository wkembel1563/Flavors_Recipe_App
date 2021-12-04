package com.example.flavors_prototype

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ShopListAdapter (
    private val recipeIngredients: ArrayList<ArrayList<Ingredient>>,
    private val recipeNames: ArrayList<String>,
    private val activity : Activity
    ) : RecyclerView.Adapter<ShopListAdapter.MyViewHolder>() {

//    private var viewPool : RecyclerView.RecycledViewPool = RecyclerView.RecycledViewPool()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        //layout inflater using the layout from data_item.xml
        val shopListView= LayoutInflater.from(parent.context).inflate(R.layout.activity_shopping_list,
            parent,false)
        return MyViewHolder(shopListView)
    }

    //will store data from each node in currentitem, and populates each card item with text from given place
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        /* bind recipe name */
        val name = recipeNames[position]
        holder.Recipe.text = name

        /* set up ingredient list recycler view */
        holder.dataItemRecyclerView.layoutManager = LinearLayoutManager(activity)//= LinearLayoutManager(activity)
        holder.dataItemRecyclerView.setHasFixedSize(true)

        /* pass to ingredients to ingredients list adapter */
        var adapter = ShopListIngredAdapter(recipeIngredients[position])
        holder.dataItemRecyclerView.adapter = adapter

//        holder.dataItemRecyclerView.setRecycledViewPool(viewPool)

    }

    override fun getItemCount(): Int {

        return recipeNames.size
    }
    //this class binds variables to the textViews in data_item
    class MyViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){

        val Recipe : TextView = itemView.findViewById(R.id.tvdishName)

        var dataItemRecyclerView: RecyclerView = itemView.findViewById(R.id.shopListIngredient)

    }

}