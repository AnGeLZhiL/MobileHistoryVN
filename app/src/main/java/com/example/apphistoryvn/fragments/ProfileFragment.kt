package com.example.apphistoryvn.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.apphistoryvn.common.Global
import com.example.apphistoryvn.databinding.FragmentInformationBinding
import com.example.apphistoryvn.databinding.FragmentProfileBinding
import java.lang.RuntimeException

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding: FragmentProfileBinding
        get() = _binding ?: throw RuntimeException()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProfileBinding.inflate(inflater)

        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val userName = Global.user!!.user_first_name
        val userFam = Global.user!!.user_second_name
        val userOtch = Global.user!!.user_midlle_name
        binding.profileName.text = userName
        binding.profileFam.text = userFam
        binding.profileOtch.text = userOtch
        binding.ratingText.text = Global.user!!.user_rating.toString()
        binding.fioText.text = userFam + " " + userName.substring(0,1) + "." + userOtch.substring(0,1) + "."
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}