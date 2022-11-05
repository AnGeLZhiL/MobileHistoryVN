package com.example.apphistoryvn.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.apphistoryvn.MenuActivity
import com.example.apphistoryvn.adapters.categoryAdapter
import com.example.apphistoryvn.common.Global
import com.example.apphistoryvn.databinding.FragmentInformationBinding
import com.example.apphistoryvn.models.CategoryViewModel
import com.example.apphistoryvn.models.categoryModel
import org.json.JSONArray
import org.json.JSONObject
import java.lang.RuntimeException
import java.util.*
import kotlin.collections.ArrayList

class InformationFragment : Fragment() {
      private lateinit var binding: FragmentInformationBinding
      val url = "https://historyvn.herokuapp.com/categories"
    private lateinit var newRecyclerView: RecyclerView
    private lateinit var newArrayList: ArrayList<categoryModel>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentInformationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val userName = Global.user!!.user_first_name
        val userFam = Global.user!!.user_second_name
        val userOtch = Global.user!!.user_midlle_name
        binding.ratingText.text = Global.user!!.user_rating.toString()
        binding.fioText.text = userFam + " " + userName.substring(0,1) + "." + userOtch.substring(0,1) + "."

        binding.categoriesRecView.layoutManager = LinearLayoutManager(context)
        binding.categoriesRecView.setHasFixedSize(true)

        var list = ArrayList<categoryModel>()
        var rq: RequestQueue = Volley.newRequestQueue(context)
        var jar = JsonArrayRequest(
            Request.Method.GET,
            url,
            null,
            {
                    response ->
                for (x in 0..response.length()-1){
                    val item = categoryModel(
                        response.getJSONObject(x).getInt("id"),
                        response.getJSONObject(x).getString("catname"),
                        response.getJSONObject(x).getString("catimg")
                    )
                    list.add(item)
                }

                Log.d("MyLog", "Item ${list}")
                binding.categoriesRecView.adapter = categoryAdapter(list)
            },
            {
                    error -> Log.d("MyLog", "Error $error")
            }
        )
        rq.add(jar)
    }
}
