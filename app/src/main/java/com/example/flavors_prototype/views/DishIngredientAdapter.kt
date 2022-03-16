package com.example.flavors_prototype.views

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.flavors_prototype.R
import com.example.flavors_prototype.models.Ingredient
import com.squareup.picasso.Picasso

class DishIngredientAdapter(
    private val dataList : ArrayList<Ingredient>
) : RecyclerView.Adapter<DishIngredientAdapter.MyViewHolder> (){

    // SET UP CLICK LISTENER FOR CARDS
    private lateinit var mListener : OnItemClickListener

    // any class that implements OnItemClickListener
    // must also implement onItemClick

    interface OnItemClickListener{
        // pass the position of the item clicked to method
        //fun onItemClick(position: Int)

        fun onSaveClick(position: Int)
    }

    fun setOnItemClickListener(listener: OnItemClickListener){
        mListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        //layout inflater using the layout from data_item.xml
        val ingredientView = LayoutInflater.from(parent.context).inflate(
            R.layout.dish_ingredient,
            parent,false)

        return MyViewHolder(ingredientView, mListener)

    }

    /* Create cards with selected ingredients */
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.cardText.text = dataList[position].name
        holder.cardQty.text = dataList[position].quantity
        Picasso.get().load(dataList[position].url).into(holder.cardImage)
    }

    /* Let the adapter know how many cards there are */
    override fun getItemCount(): Int {
        return dataList.size
    }

    /* View Holder template for ingredient cards */
    class MyViewHolder(itemView : View, listener: OnItemClickListener) : RecyclerView.ViewHolder(itemView){

        /* connect to save button of card */
        private var save : Button = itemView.findViewById(R.id.saveIngredient)

        /* connect to name textview of card */
        var cardText : TextView = itemView.findViewById(R.id.tvDishIngredient)

        /* connect to quantity textview of card */
        var cardQty: TextView = itemView.findViewById(R.id.tvDishIngredient2)

        /* connect to image of card */
        var cardImage : ImageView = itemView.findViewById(R.id.dishIngredientImage)

        init {

            save.setOnClickListener {
                listener.onSaveClick(adapterPosition)
            }

        }

    }

}