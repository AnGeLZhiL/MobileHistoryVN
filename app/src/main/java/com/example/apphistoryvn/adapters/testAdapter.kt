package com.example.apphistoryvn.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.apphistoryvn.R
import com.example.apphistoryvn.models.categoryModel
import com.example.apphistoryvn.models.testModel
import com.squareup.picasso.Picasso

class testAdapter(private val testsList : ArrayList<testModel>) : RecyclerView.Adapter<testAdapter.testViewHolder>() {
    class testViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameTest = itemView.findViewById<TextView>(R.id.testName)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): testViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.test_item, parent, false)
        return testAdapter.testViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: testViewHolder, position: Int) {
        val currentItem = testsList[position]
        holder.nameTest.text = currentItem.test_title
    }

    override fun getItemCount(): Int {
        return testsList.size
    }
}