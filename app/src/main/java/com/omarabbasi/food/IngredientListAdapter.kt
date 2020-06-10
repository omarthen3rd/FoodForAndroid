package com.omarabbasi.food

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import java.net.URLEncoder.encode
import java.nio.charset.StandardCharsets

class IngredientListAdapter(private val dataSource: List<Ingredient>)
    : RecyclerView.Adapter<IngredientListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val context = parent.context
        val inflater = LayoutInflater.from(context)

        val ingredientItemView = inflater.inflate(R.layout.ingredient_list_item, parent, false)

        return ViewHolder(ingredientItemView)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        var ingredient = dataSource[position]

        val name = holder.nameView
        name.text = "${ingredient.quantity} ${ingredient.name}"

        val image = holder.imageView
        val url = "https://www.themealdb.com/images/ingredients/${ingredient.name}.png"
        Log.d("url", url)
        Picasso.get().load(url).into(image)
    }

    override fun getItemCount() = dataSource.count()

    class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {

        val imageView: ImageView = v.findViewById(R.id.ingredient_image) as ImageView
        val nameView: TextView = v.findViewById(R.id.ingredient_name) as TextView

    }

}