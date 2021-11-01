package com.example.flavors_prototype

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

//this class stores the data from every CountryName node in an array
class MyAdapter(
    private val dataList : ArrayList<Recipe>,
    private val listener : OnItemClickListener
    ) : RecyclerView.Adapter<MyAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        //layout inflater using the layout from data_item.xml
        val recipeView = LayoutInflater.from(parent.context).inflate(R.layout.data_item,
            parent,false)
        return MyViewHolder(recipeView)

    }

    //will store data from each node in currentitem, and populates each card item with text from given place
    // this method is called everytime a card scrolls onto screen
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val currentitem = dataList[position]

        holder.nameOfPlace.text = currentitem.Place
        holder.Recipe.text = currentitem.Recipe
        //holder.prepTime.text = currentitem.PrepTime
        //holder.cookTime.text = currentitem.CookTime
        //holder.ingredients.text = currentitem.Ingredients
        //holder.instructions.text = currentitem.Instructions

    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    //this class binds variables to the textViews in data_item
    // setting the clickListener for the recycler view card in here as well
    inner class MyViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView),
    View.OnClickListener{

        val nameOfPlace : TextView = itemView.findViewById(R.id.tvnameOfPlace)
        val Recipe : TextView = itemView.findViewById(R.id.tvrecipe)
       // val prepTime : TextView = itemView.findViewById(R.id.tvpreptime)
       // val cookTime : TextView = itemView.findViewById(R.id.tvcooktime)
       // val ingredients : TextView = itemView.findViewById(R.id.tvingredients)
       // val instructions : TextView = itemView.findViewById(R.id.tvinstructions)

        // set a clickListener on itemview to activate DishView after
        // clicking anywhere on the recyclerview card
        init {
            // 'this' is the MyViewHolder class
            // View.onClickListener is needed above as an interface
            itemView.setOnClickListener(this)
        }

        // define what happens when an itemview (card) is clicked
        override fun onClick(p0: View?) {

            // forward the click event to the screen displaying the recycler view.
            // 'listener' = activity calling the adapter
            // position = pos of click
            val position = adapterPosition
            if(position != RecyclerView.NO_POSITION) { // make sure pos still valid
                listener.onItemClick(position)
            }
        }
    }

    // any class that implements OnItemClickListener
    // must also implement onItemClick
    // the DataActivity will implement this
    interface OnItemClickListener{
        // pass the position of the item clicked to method
        fun onItemClick(position: Int)
    }

}