package com.example.flavors_prototype

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CustomAdapter(private val dataSet: MutableList<String>) : RecyclerView.Adapter<CustomAdapter.ViewHolder>() {
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvCountry: TextView

        init {
            tvCountry = view.findViewById(R.id.tvCountry) // Initialize your All views present in list items
        }
    }

    //Create new views
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        //Create a new view, which defines the UI of list item
        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.item_list, viewGroup, false)

        return ViewHolder(view)
    }

   //Bind the contents of a view
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        //Get elements from your dataset at this position and replace the contents of the view with that element
        viewHolder.tvCountry.text = dataSet[position]
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

}