package com.example.flavors_prototype

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

//this class stores the data from every CountryName node in an array
class MyAdapter(private val dataList : ArrayList<Recipe>) : RecyclerView.Adapter<MyAdapter.MyViewHolder>() {

    //

    lateinit var LikesRef : DatabaseReference
    var LikeChecker : Boolean = false;
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        LikesRef = FirebaseDatabase.getInstance().getReference().child("Likes")
        //layout inflater using the layout from data_item.xml
        val recipeView = LayoutInflater.from(parent.context).inflate(R.layout.data_item,
            parent,false)
        return MyViewHolder(recipeView)
    }
    //will store data from each node in currentitem, and populates each card item with text from given place
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentUserID : String = FirebaseAuth.getInstance().currentUser?.uid.toString()
        val RecipeKey = (position).toString()
        val currentitem = dataList[position]

        holder.nameOfPlace.text = currentitem.Place
        holder.Recipe.text = currentitem.Recipe
        holder.prepTime.text = currentitem.PrepTime
        holder.cookTime.text = currentitem.CookTime
        holder.ingredients.text = currentitem.Ingredients
        holder.instructions.text = currentitem.Instructions
        holder.LikePostButton.setOnClickListener {

            LikeChecker = true;

            LikesRef.addValueEventListener(object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (LikeChecker.equals(true))
                    {
                        if(snapshot.child(RecipeKey).hasChild(currentUserID))
                        {
                            LikesRef.child(RecipeKey).child(currentUserID).removeValue()
                            LikeChecker = false
                        }
                        else
                        {
                            LikesRef.child(RecipeKey).child(currentUserID).setValue(true)
                            LikeChecker = false
                        }
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            })
        }
        holder.CommentButton.setOnClickListener {

            startActivity(holder.context,holder.StartComment, Bundle())
        }

        holder.setLikeButtonStatus(RecipeKey)



    }

    override fun getItemCount(): Int {

        return dataList.size
    }
    //this class binds variables to the textViews in data_item
    class MyViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        val context: Context = itemView.context
        val StartComment = Intent(context, CommentActivity::class.java)
        var LikesRef : DatabaseReference = FirebaseDatabase.getInstance().getReference().child("Likes")
        val currentUserID : String = FirebaseAuth.getInstance().currentUser?.uid.toString()
        var countLikes = 0
        val LikePostButton : ImageButton = itemView.findViewById(R.id.like_button)
        val NumberOfLikes : TextView = itemView.findViewById(R.id.numberOfLikes)
        val CommentButton : ImageButton = itemView.findViewById(R.id.comment_button)

        val nameOfPlace : TextView = itemView.findViewById(R.id.tvnameOfPlace)
        val Recipe : TextView = itemView.findViewById(R.id.tvrecipe)
        val prepTime : TextView = itemView.findViewById(R.id.tvpreptime)
        val cookTime : TextView = itemView.findViewById(R.id.tvcooktime)
        val ingredients : TextView = itemView.findViewById(R.id.tvingredients)
        val instructions : TextView = itemView.findViewById(R.id.tvinstructions)

        fun setLikeButtonStatus(RecipeKey : String)
        {

            LikesRef.addValueEventListener(object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {

                    if(snapshot.child(RecipeKey).hasChild(currentUserID))
                    {
                        countLikes = snapshot.child(RecipeKey).childrenCount.toInt()
                        LikePostButton.setImageResource(R.drawable.like)
                        NumberOfLikes.setText(countLikes.toString())
                    }
                    else
                    {
                        countLikes = snapshot.child(RecipeKey).childrenCount.toInt()
                        LikePostButton.setImageResource(R.drawable.dislike)
                        NumberOfLikes.setText(countLikes.toString())
                    }

                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            })
        }

        //fun startCommentActivity()
        //{
        //    startActivity(context,StartComment, Bundle.EMPTY)
        //}

    }



}