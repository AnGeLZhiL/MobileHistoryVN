package com.example.apphistoryvn.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.android.volley.AuthFailureError
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.apphistoryvn.common.Global
import com.example.apphistoryvn.databinding.ActivityAuthorizationBinding
import com.example.apphistoryvn.models.userModel
import org.json.JSONObject
import java.security.MessageDigest


class AuthorizationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAuthorizationBinding
    val url = "https://historyvn.herokuapp.com/login"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthorizationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.textSignInClick.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
        }

        binding.signInClick.setOnClickListener {
            if(binding.editTextLogin.text.isNotEmpty() and binding.editPassword.text.isNotEmpty()){
                SignIn(binding.editTextLogin.text.toString(), binding.editPassword.text.toString())
            } else {
                if (binding.editTextLogin.text.isEmpty()) binding.editTextLogin.error = "Поле не заполнено"
                if (binding.editPassword.text.isEmpty()) binding.editPassword.error = "Поле не заполнено"
            }
        }
    }

    private fun hashString(input: String, algorithm: String): String {
        return MessageDigest
            .getInstance(algorithm)
            .digest(input.toByteArray())
            .fold("", { str, it -> str + "%02x".format(it) })
    }

    private fun SignIn(login: String, password: String) {
        val volleyRequestQueue = Volley.newRequestQueue(this)
        val parameters: MutableMap<String, String> = HashMap()
        parameters.put("user_login",login);
        parameters.put("user_password",hashString(password, "SHA-256"));
        val strReq: StringRequest = object : StringRequest(
            Method.POST,
            url,
            {
                    response ->
                try {
                    val responseObj = JSONObject(response)
                    parseUserData(responseObj)
                    Global.id_user = responseObj.getInt("id")
                    Toast.makeText(this,"Успешная авторизация", Toast.LENGTH_LONG).show()
                    startActivity(Intent(this, MenuActivity::class.java))
                    finishAffinity();
                } catch (e: Exception){
                    Log.e("response_error", "problem occurred")
                    Toast.makeText(this,"Ошибка авторизации", Toast.LENGTH_LONG).show()
                    e.printStackTrace()
                }
            },
            {
                    error ->
                Toast.makeText(this,"Пользователь не найден", Toast.LENGTH_LONG).show()
            }
        ) {
            override fun getParams(): MutableMap<String, String> {
                return parameters;
            }

            @Throws(AuthFailureError::class)
            override fun getHeaders(): Map<String, String> {

                val headers: MutableMap<String, String> = HashMap()
                return headers
            }
        }
        volleyRequestQueue.add(strReq)
    }

    private fun parseUserData(resultObject: JSONObject){
        Global.user = userModel(
            id = resultObject.getInt("id"),
            user_first_name = resultObject.getString("user_last_name"),
            user_second_name = resultObject.getString("user_first_name"),
            user_midlle_name = resultObject.getString("user_midlle_name"),
            user_login = resultObject.getString("user_login"),
            user_rating = resultObject.getDouble("user_rating")
        )
    }
}