package com.example.apphistoryvn

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.android.volley.AuthFailureError
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.apphistoryvn.databinding.ActivityAuthorizationBinding
import com.example.apphistoryvn.databinding.ActivitySignUpBinding
import org.json.JSONObject
import java.security.MessageDigest

class SignUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding
    val url = "https://historyvn.herokuapp.com/register"
    private val log = "[a-zA-Z0-9]+@[0-9a-z]+\\.+[a-z]{1,3}"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.textSignInClick.setOnClickListener {
            startActivity(Intent(this, AuthorizationActivity::class.java))
        }
        binding.signUpClick.setOnClickListener {
            if(binding.editFirstNameText.text.isNotEmpty() and binding.editLastNameText.text.isNotEmpty() and
                binding.editMiddleNameText.text.isNotEmpty() and binding.editLoginText.text.isNotEmpty() and
                binding.editPasswordText.text.isNotEmpty() and binding.editPasswordConfirmationText.text.isNotEmpty()){
                if(binding.editLoginText.text.toString().trim().matches(log.toRegex())) {
                    if (binding.editPasswordText.text.toString() == binding.editPasswordConfirmationText.text.toString()){
                        SignUp(binding.editFirstNameText.text.toString(), binding.editLastNameText.text.toString(),
                            binding.editMiddleNameText.text.toString(), binding.editLoginText.text.toString(),
                            binding.editPasswordText.text.toString());
                    } else
                        if(binding.editPasswordText.text.isEmpty()) binding.editPasswordText.error = "Пароли не совпадают"
                } else {
                    if(binding.editLoginText.text.isEmpty()) binding.editLoginText.error = "Данные заполнены некоректно, логин должен соответствовать почте"
                }
            } else {
                if(binding.editFirstNameText.text.isEmpty()) binding.editFirstNameText.error = "Поле не заполнено"
                if(binding.editLastNameText.text.isEmpty()) binding.editLastNameText.error = "Поле не заполнено"
                if(binding.editMiddleNameText.text.isEmpty()) binding.editMiddleNameText.error = "Поле не заполнено"
                if(binding.editLoginText.text.isEmpty()) binding.editLoginText.error = "Поле не заполнено"
                if(binding.editPasswordText.text.isEmpty()) binding.editPasswordText.error = "Поле не заполнено"
                if(binding.editPasswordConfirmationText.text.isEmpty()) binding.editPasswordConfirmationText.error = "Поле не заполнено"
            }
        }
    }

    private fun hashString(input: String, algorithm: String): String {
        return MessageDigest
            .getInstance(algorithm)
            .digest(input.toByteArray())
            .fold("", { str, it -> str + "%02x".format(it) })
    }

    private fun SignUp(userFirstName: String, userLastName: String, userMiddleName: String, userLogin: String, passwordText: String) {
        val volleyRequestQueue = Volley.newRequestQueue(this)
        val parameters: MutableMap<String, String> = HashMap()
        parameters.put("user_last_name",userFirstName);
        parameters.put("user_first_name",userLastName);
        parameters.put("user_midlle_name",userMiddleName);
        parameters.put("user_login",userLogin);
        parameters.put("user_password",hashString(passwordText, "SHA-256"));
        val strReq: StringRequest = object : StringRequest(
            Method.POST,
            url,
            {
                response ->
                try {
                    val responseObj = JSONObject(response)
                    Toast.makeText(this,"Успешная регистрация", Toast.LENGTH_LONG).show()
                    startActivity(Intent(this, AuthorizationActivity::class.java))
                    finishAffinity();
                } catch (e: Exception){
                    Log.e("response_error", "problem occurred")
                    Toast.makeText(this,"Ошибка регистрации", Toast.LENGTH_LONG).show()
                    if(binding.editLoginText.text.isEmpty()) binding.editLoginText.error = "Логин уже используется"
                    e.printStackTrace()
                }
            },
            {
                    error ->
                Log.e("error", "problem occurred, volley error: " + error.message)
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
}