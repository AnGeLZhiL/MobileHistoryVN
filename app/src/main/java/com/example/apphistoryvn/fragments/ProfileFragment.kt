package com.example.apphistoryvn.fragments

import android.annotation.SuppressLint
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
import com.example.apphistoryvn.adapters.testUserAdapter
import com.example.apphistoryvn.common.Global
import com.example.apphistoryvn.databinding.FragmentProfileBinding
import com.example.apphistoryvn.models.categoryModel
import com.example.apphistoryvn.models.userTestModel

class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding
    val url = "https://historyvn.herokuapp.com/"
    private lateinit var newRecyclerView: RecyclerView
    private lateinit var newArrayList: ArrayList<categoryModel>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val userName = Global.user!!.user_first_name
        val userFam = Global.user!!.user_second_name
        val userOtch = Global.user!!.user_midlle_name
        binding.profileName.text = userName
        binding.profileFam.text = userFam
        binding.profileOtch.text = userOtch
        binding.ratingText.text = Global.user!!.user_rating.toString()
        binding.fioText.text = userFam + " " + userName.substring(0,1) + "." + userOtch.substring(0,1) + "."
        binding.recyclerUserTest.layoutManager = LinearLayoutManager(context)
        binding.recyclerUserTest.setHasFixedSize(true)
        getUserTests()
    }

    fun getUserTests(){
        var list = ArrayList<userTestModel>()
        var rq: RequestQueue = Volley.newRequestQueue(context)
        var jar = JsonArrayRequest(
            Request.Method.GET,
            url + "userstests/" + Global.id_user.toString(),
            null,
            {
                    response ->
                binding.recyclerUserTest.visibility = View.VISIBLE
                binding.testUserLable.visibility = View.GONE
                for (x in 0..response.length()-1){
                    val item = userTestModel(
                        response.getJSONObject(x).getInt("id_user"),
                        response.getJSONObject(x).getString("test_title"),
                        response.getJSONObject(x).getDouble("sumcur")
                    )
                    list.add(item)
                }

                Log.d("MyLog", "Item ${list}")
                binding.recyclerUserTest.adapter = testUserAdapter(list)
            },
            {
                    error ->
                binding.recyclerUserTest.visibility = View.GONE
                binding.testUserLable.visibility = View.VISIBLE
            }
        )
        rq.add(jar)
    }
}