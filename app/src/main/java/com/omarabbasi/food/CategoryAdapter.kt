package com.omarabbasi.food

import android.content.Context
import android.content.res.AssetManager
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso
import java.io.File

class CategoryAdapter(private val context: Context,
                      private val dataSource: List<Category>) : BaseAdapter() {

    private val inflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)
            as LayoutInflater

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        val rowView = inflater.inflate(R.layout.category_list, parent, false)
        val category = getItem(position) as Category

        val titleView = rowView.findViewById(R.id.category_title) as TextView
        titleView.text = category.strCategory

        val imageView = rowView.findViewById(R.id.category_image) as ImageView
        var imageName = category.strCategory.toLowerCase() + ".png"
        val imageFile = context.assets.open(imageName)
        val image = Drawable.createFromStream(imageFile, null)
        imageView.setImageDrawable(image)

        return rowView

    }

    override fun getItem(position: Int): Any = dataSource[position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getCount(): Int = dataSource.size

}