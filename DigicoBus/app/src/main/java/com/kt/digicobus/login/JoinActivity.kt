package com.kt.digicobus.login

import android.graphics.Paint.Join
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

        //알림창 띄우기
        var dialog_listener = MyDialog(this); //다이얼로그 선언

        binding.btnJoin.setOnClickListener{
            //회원가입 내역 백엔드로 보내기

            //다이얼로그 띄우기
            dialog_listener.show();//띄우기
        }
    }
}