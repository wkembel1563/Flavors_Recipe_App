package com.example.flavors_prototype.views
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.flavors_prototype.R
import com.example.flavors_prototype.models.Recipe
import com.squareup.picasso.Picasso
//this dish adapter is for the screen after a country is selected
//this will give list of dishes with thumbnail picture
//constructor takes and array of Recipes
class DishAdapter(
    private val dataList : ArrayList<Recipe>,
    ) : RecyclerView.Adapter<DishAdapter.MyViewHolder>() {

    // SET UP CLICK LISTENER FOR CARDS
    private lateinit var mListener : OnItemClickListener
    // any class that implements OnItemClickListener
    // must also implement onItemClick
    interface OnItemClickListener{
        // pass the position of the item clicked to method
        //this int position will be called in DisViewActivity
        //to know which dish information to display after selection
        fun onItemClick(position: Int)
    }
    //this function will listen for clicks on list items
    fun setOnItemClickListener(listener: OnItemClickListener){
        mListener = listener
    }
    //function executes when viewholder is created
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        //layout inflater using the layout from data_item.xml
        val recipeView = LayoutInflater.from(parent.context).inflate(
            R.layout.dish_item,
            parent,false)

        //returns view holder with listener for clicks on list items
        return MyViewHolder(recipeView, mListener)

    }

    // will store data from each node in currentItem, and populates each card item with text from given dish
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentitem = dataList[position]
        val imagePath = currentitem.image

        holder.nameOfdish.text = currentitem.Recipe
        Picasso.get().load(imagePath).into(holder.dishImage)

    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    //this class binds variables to the textViews in data_item
    // setting the clickListener for the recycler view card in here as well
    class MyViewHolder(itemView : View, listener: OnItemClickListener) : RecyclerView.ViewHolder(itemView){

        // set a clickListener on itemview to activate DishView after
        // clicking anywhere on the recyclerview card
        //var btn : Button = itemView.findViewById()

        var nameOfdish : Button = itemView.findViewById(R.id.dishButton)
        var  dishImage : ImageView = itemView.findViewById(R.id.recipeListImage)
        init {
            nameOfdish.setOnClickListener(View.OnClickListener {
                listener.onItemClick(adapterPosition)
            })
        }


    }
}