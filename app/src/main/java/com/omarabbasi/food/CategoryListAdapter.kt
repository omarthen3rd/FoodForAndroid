package com.omarabbasi.food

import android.content.res.AssetManager
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CategoryListAdapter(private val dataSource: List<Category>,
                          private val clickListener: (Category) -> Unit) :
    RecyclerView.Adapter<CategoryListAdapter.ViewHolder>() {

    var assets: AssetManager? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)

        assets = parent.context.assets

        val categoryItemView = inflater.inflate(R.layout.category_list_item, parent, false)

        return ViewHolder(categoryItemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        var category = dataSource[position]

        holder.bind(category, clickListener)

        val name = holder.nameView
        name.text = category.strCategory

        val imageView = holder.imageView
        var imageName = category.strCategory.toLowerCase() + ".png"
        val imageFile = assets?.open(imageName)
        val imageDrawable = Drawable.createFromStream(imageFile, null)
        imageView.setImageDrawable(imageDrawable)

    }

    override fun getItemCount(): Int = dataSource.size

    class ViewHolder(catView: View) : RecyclerView.ViewHolder(catView) {

        val imageView: ImageView = catView.findViewById(R.id.category_image) as ImageView
        val nameView: TextView = catView.findViewById(R.id.category_title) as TextView

        fun bind(category: Category, clickListener: (Category) -> Unit) {

            // todo
            itemView.setOnClickListener {
                clickListener(category)
            }

        }

    }


}