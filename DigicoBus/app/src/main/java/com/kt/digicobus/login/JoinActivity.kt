package com.kt.digicobus.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.kt.digicobus.R
import com.kt.digicobus.databinding.ActivityJoinBinding
import com.kt.digicobus.databinding.ActivityLoginBinding

class JoinActivity : AppCompatActivity() {
    private lateinit var binding: ActivityJoinBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityJoinBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }
}