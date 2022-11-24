package com.example.apphistoryvn.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.example.apphistoryvn.adapters.categoryAdapter
import com.example.apphistoryvn.adapters.testAdapter
import com.example.apphistoryvn.common.Global
import com.example.apphistoryvn.databinding.FragmentInformationBinding
import com.example.apphistoryvn.databinding.FragmentProfileBinding
import com.example.apphistoryvn.databinding.FragmentTestsBinding
import com.example.apphistoryvn.models.categoryModel
import com.example.apphistoryvn.models.testModel
import java.lang.RuntimeException

class TestsFragment : Fragment() {
    private lateinit var binding: FragmentTestsBinding
    val url = "https://historyvn.herokuapp.com/tests"
    private lateinit var newRecyclerView: RecyclerView
    private lateinit var newArrayList: ArrayList<categoryModel>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTestsBinding.inflate(inflater, container, false)
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

        binding.testRecView.layoutManager = LinearLayoutManager(context)
        binding.testRecView.setHasFixedSize(true)

        var list = ArrayList<testModel>()
        var rq: RequestQueue = Volley.newRequestQueue(context)
        var jar = JsonArrayRequest(
            Request.Method.GET,
            url,
            null,
            {
                    response ->
                for (x in 0..response.length()-1){
                    val item = testModel(
                        response.getJSONObject(x).getInt("id"),
                        response.getJSONObject(x).getString("test_title"),
                        response.getJSONObject(x).getInt("test_category")
                    )
                    list.add(item)
                }

                Log.d("MyLog", "Item ${list}")
                binding.testRecView.adapter = testAdapter(list)
            },
            {
                    error -> Log.d("MyLog", "Error $error")
            }
        )
        rq.add(jar)
    }
}