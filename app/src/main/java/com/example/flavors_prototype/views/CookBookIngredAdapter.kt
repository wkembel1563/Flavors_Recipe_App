package com.example.flavors_prototype.views

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.flavors_prototype.models.Ingredient
import com.example.flavors_prototype.R

class CookBookIngredAdapter(
    private val ingredientList : ArrayList<Ingredient>
) : RecyclerView.Adapter<CookBookIngredAdapter.MyViewHolder> (){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        //layout inflater using the layout from data_item.xml
        val ingredientView = LayoutInflater.from(parent.context).inflate(
            R.layout.cookbook_recycle_item,
            parent,false)

        return MyViewHolder(ingredientView)

    }

    /* Create cards with selected ingredients */
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        holder.cardText.text = ingredientList[position].name.toString()
        holder.cardQty.text = ingredientList[position].quantity.toString()

    }

    /* Let the adapter know how many cards there are */
    override fun getItemCount(): Int {
        return ingredientList.size
    }

    /* View Holder template for ingredient cards */
    class MyViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){

        /* connect to textview of card */
        var cardText : TextView = itemView.findViewById(R.id.cbIngredText)
        var cardQty : TextView = itemView.findViewById(R.id.cbIngredQty)

    }

}