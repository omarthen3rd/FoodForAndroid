package com.omarabbasi.food

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class RecipeListAdapter(private val dataSource: List<BasicRecipe>,
                        private val clickListener: (BasicRecipe) -> Unit)
    : RecyclerView.Adapter<RecipeListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)

        val recipeItemView = inflater.inflate(R.layout.recipe_list_item, parent, false)

        return ViewHolder(recipeItemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        var basicRecipe = dataSource[position]

        holder.bind(basicRecipe, clickListener)

        val name = holder.nameView
        name.text = basicRecipe.strMeal

        val image = holder.imageView
        Picasso.get().load(basicRecipe.strMealThumb).into(image)

    }

    override fun getItemCount(): Int = dataSource.size

    class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {

        val imageView: ImageView = v.findViewById(R.id.recipe_image) as ImageView
        val nameView: TextView = v.findViewById(R.id.recipe_name) as TextView

        fun bind(recipe: BasicRecipe, clickListener: (BasicRecipe) -> Unit) {

            // todo
            itemView.setOnClickListener {
                clickListener(recipe)
            }

        }

    }
}
