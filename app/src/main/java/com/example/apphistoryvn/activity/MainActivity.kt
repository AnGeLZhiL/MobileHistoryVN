package com.example.apphistoryvn.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.animation.AnimationUtils
import com.example.apphistoryvn.R
import com.example.apphistoryvn.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sideAnimation = AnimationUtils.loadAnimation(this, R.anim.slide)

        binding.logoText.startAnimation(sideAnimation)

        Handler().postDelayed({
            val Aloading = Intent(this, AuthorizationActivity::class.java)
            startActivity(Aloading)
            finish();
        }, 2000)

    }
}