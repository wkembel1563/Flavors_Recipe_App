package com.example.flavors_prototype

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CommentsAdapter(private val commentList : ArrayList<Comments>) : RecyclerView.Adapter<CommentsAdapter.ViewHolder>()  {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        //layout inflater using the layout from data_item.xml
        val commentView = LayoutInflater.from(parent.context).inflate(R.layout.comments_layout, parent, false)
        return ViewHolder(commentView)
    }
    //will store data from each node in currentitem, and populates each card item with text from given place
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        //current position of item in array
        val currentitem = commentList[position]

        holder.userID.text = "A Foodie "/*currentitem.uid*/
        holder.comment.text = currentitem.comment
        holder.date.text = currentitem.date
        holder.time.text = currentitem.time

    }

    override fun getItemCount(): Int {

        return commentList.size
    }
    //this class binds variables to the textViews in data_item
    class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){

        val userID : TextView = itemView.findViewById(R.id.comment_userId)
        val comment : TextView = itemView.findViewById(R.id.comment_text)
        val date : TextView = itemView.findViewById(R.id.comment_date)
        val time : TextView = itemView.findViewById(R.id.comment_time)

    }



}