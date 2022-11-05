package com.example.apphistoryvn.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.apphistoryvn.databinding.FragmentProfileBinding
import com.example.apphistoryvn.databinding.FragmentTestsBinding
import java.lang.RuntimeException

class TestsFragment : Fragment() {
    private var _binding: FragmentTestsBinding? = null
    private val binding: FragmentTestsBinding
        get() = _binding ?: throw RuntimeException()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTestsBinding.inflate(inflater)

        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        binding.button.setOnClickListener {
//            Toast.makeText(activity, "Test Binding", Toast.LENGTH_LONG).show()
//            println("Test Binding")
//        }
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}