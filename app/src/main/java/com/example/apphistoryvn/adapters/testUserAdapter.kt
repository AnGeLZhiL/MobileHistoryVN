package com.example.apphistoryvn.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.apphistoryvn.R
import com.example.apphistoryvn.models.userTestModel

class testUserAdapter(private val testUserList : ArrayList<userTestModel>) : RecyclerView.Adapter<testUserAdapter.testUserViewHolder>() {
    class testUserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameTest =itemView.findViewById<TextView>(R.id.testName)
        val currentTest =itemView.findViewById<TextView>(R.id.testCurrent)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): testUserViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.test_user_item, parent, false)
        return testUserAdapter.testUserViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: testUserViewHolder, position: Int) {
        val currentItem = testUserList[position]
        holder.nameTest.text = currentItem.test_title
        holder.currentTest.text = currentItem.sumcur.toString()
    }

    override fun getItemCount(): Int {
        return testUserList.size
    }
}