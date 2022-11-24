package com.example.apphistoryvn.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.apphistoryvn.common.Global
import com.example.apphistoryvn.databinding.ActivitySignUpBinding
import com.example.apphistoryvn.models.userModel
import org.json.JSONObject
import java.security.MessageDigest

class SignUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding
    val url = "https://historyvn.herokuapp.com/register"
    var url_login = "https://historyvn.herokuapp.com/users"
    private val log = "[a-zA-Z0-9]+@[0-9a-z]+\\.+[a-z]{1,3}"
    private val fioRegex = "[A-Za-zА-Яа-я-]*"

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
                    if (binding.editLastNameText.text.toString().trim().matches(fioRegex.toRegex()) and
                        binding.editFirstNameText.text.toString().trim().matches(fioRegex.toRegex()) and
                        binding.editMiddleNameText.text.toString().trim().matches(fioRegex.toRegex())){
                        if(binding.editLoginText.text.toString().trim().matches(log.toRegex())) {
                            if (binding.editPasswordText.text.toString() == binding.editPasswordConfirmationText.text.toString()){
                                SignUp(binding.editFirstNameText.text.toString(), binding.editLastNameText.text.toString(),
                                    binding.editMiddleNameText.text.toString(), binding.editLoginText.text.toString(),
                                    binding.editPasswordText.text.toString());
                            } else
                                binding.editPasswordText.error = "Пароли не совпадают"
                        } else {
                            binding.editLoginText.error = "Данные заполнены некоректно, логин должен соответствовать почте"
                        }
                    } else {
                        if(!binding.editFirstNameText.text.toString().trim().matches(fioRegex.toRegex())) binding.editFirstNameText.error = "Неверный формат"
                        if(!binding.editLastNameText.text.toString().trim().matches(fioRegex.toRegex())) binding.editLastNameText.error = "Неверный формат"
                        if(!binding.editMiddleNameText.text.toString().trim().matches(fioRegex.toRegex())) binding.editMiddleNameText.error = "Неверный формат"
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
                    SignIn(responseObj.getInt("id"))
                } catch (e: Exception){
                    Log.e("response_error", "$e")
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

    private fun SignIn(id: Int) {
        val queue = Volley.newRequestQueue(this)
        url_login = url_login + "/$id"
        val stringRequest = StringRequest(
            Request.Method.GET,
            url_login,
            {
                response ->
                val obj = JSONObject(response)
                parseUserData(obj)
                startActivity(Intent(this, MenuActivity::class.java))
                finishAffinity();
            },
            {
                error -> Toast.makeText(this,"Ошибка авторизации", Toast.LENGTH_LONG).show()
            }
        )
        queue.add(stringRequest)
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