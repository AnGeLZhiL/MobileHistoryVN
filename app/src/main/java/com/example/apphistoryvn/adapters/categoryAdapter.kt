package com.example.apphistoryvn.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.apphistoryvn.R
import com.example.apphistoryvn.databinding.FragmentInformationBinding
import com.example.apphistoryvn.models.categoryModel
import com.squareup.picasso.Picasso
import java.nio.file.Files.size

class categoryAdapter(private val categoryList : ArrayList<categoryModel>) : RecyclerView.Adapter<categoryAdapter.categoryViewHolder>() {

    class categoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name =itemView.findViewById<TextView>(R.id.categoryName)
        val img =itemView.findViewById<ImageView>(R.id.categoryImg)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): categoryViewHolder {

        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.categories_item, parent, false)
        return categoryViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: categoryViewHolder, position: Int) {
        val currentItem = categoryList[position]
        holder.name.text = currentItem.name
        Picasso.get().load(currentItem.img).into(holder.img)
    }

    override fun getItemCount(): Int {
        return categoryList.size
    }
}