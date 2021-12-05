package com.example.flavors_prototype

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.util.*

class ListViewAdapter(private val context: Context, private val list: ArrayList<String>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

        private inner class ViewHolder internal constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {

            internal var tvLabel: TextView

            init {
                tvLabel = itemView.findViewById(R.id.tvdish) // Initialize your All views prensent in list items
            }

            internal fun bind(position: Int) {
                // This method will be called anytime a list item is created or update its data
                //Do your stuff here

                val currentitem = list[position]



                tvLabel.text = currentitem
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_list, parent, false))
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            (holder as ViewHolder).bind(position)
        }

        override fun getItemCount(): Int {
            return list.size
        }
    }


