package com.example.flavors_prototype

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ShopListIngredAdapter(
    private val ingredientList : ArrayList<Ingredient>
    ) : RecyclerView.Adapter<ShopListIngredAdapter.MyViewHolder> (){

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

            //layout inflater using the layout from data_item.xml
            val ingredientView = LayoutInflater.from(parent.context).inflate(R.layout.shoplist_dataitem,
                parent,false)

            return MyViewHolder(ingredientView)

        }

        /* Create cards with selected ingredients */
        override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

            holder.cardText.text = ingredientList[position].name.toString()

        }

        /* Let the adapter know how many cards there are */
        override fun getItemCount(): Int {
            return ingredientList.size
        }

        /* View Holder template for ingredient cards */
        class MyViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){

            /* connect to textview of card */
            var cardText : TextView = itemView.findViewById(R.id.shopListIngredText)

        }

    }