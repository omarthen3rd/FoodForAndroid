package com.omarabbasi.food

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class RecipeListAdapter(private val dataSource: List<BasicRecipe>)
    : RecyclerView.Adapter<RecipeListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeListAdapter.ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)

        val recipeItemView = inflater.inflate(R.layout.category_detail_item, parent, false)

        return ViewHolder(recipeItemView)
    }

    override fun onBindViewHolder(holder: RecipeListAdapter.ViewHolder, position: Int) {

        var basicRecipe = dataSource.get(position)

        val name = holder.nameView
        name.setText(basicRecipe.strMeal)

        val image = holder.imageView
        Picasso.get().load(basicRecipe.strMealThumb).into(image)

    }

    override fun getItemCount(): Int = dataSource.size

    class ViewHolder(v: View) : RecyclerView.ViewHolder(v), View.OnClickListener {

        val imageView: ImageView = v.findViewById(R.id.recipe_image) as ImageView
        val nameView: TextView = v.findViewById(R.id.recipe_name) as TextView

        override fun onClick(v: View) {
            Log.d("RecyclerView", "CLICK!")
        }

    }
}
