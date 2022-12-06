package com.kt.digicobus.login

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.kt.digicobus.MainActivity
import com.kt.digicobus.R
import com.kt.digicobus.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val items = resources.getStringArray(R.array.login_spinner_array)
        val myAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, items)
        binding.spinner.adapter = myAdapter


        binding.btnLogin.setOnClickListener{
            // 로그인
            val userId = binding.editId.text.toString()
            val userPw = binding.editPassword.text.toString()

            val loginService = LoginService()
            if (loginService.requestLogin(userId, userPw)) {
                // 로그인 성공
                val intent = Intent(this, MainActivity::class.java)

                intent.putExtra("userId", userId)
                intent.putExtra("userPw", userPw)

                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
                startActivity(intent)
            } else {
                // 로그인 실패
                Toast.makeText(applicationContext, "로그인에 실패하셨습니다.", Toast.LENGTH_SHORT).show()
            }
        }


    }
}