package com.example.flavors_prototype

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CountryAdapter(private val dataSet: MutableList<String>) : RecyclerView.Adapter<CountryAdapter.ViewHolder>() {
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvCountry: TextView

        init {
            tvCountry = view.findViewById(R.id.tvCountry) // Initialize your All views present in list items
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

    /*/Create new views
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
    }*/

}