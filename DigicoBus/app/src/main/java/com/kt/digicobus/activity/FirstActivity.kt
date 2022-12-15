package com.kt.digicobus.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.kt.digicobus.R
import com.kt.digicobus.databinding.ActivityFirstBinding
import com.kt.digicobus.databinding.ActivityMainBinding

class FirstActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFirstBinding

    // 뒤로가기 버튼을 눌렀던 시간 저장
    private var backKeyPressedTime: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFirstBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnClick.setOnClickListener{
            var intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onBackPressed() {
        // 기존의 뒤로가기 버튼의 기능 제거
        // super.onBackPressed();

        // 2000 milliseconds = 2 seconds
        if (System.currentTimeMillis() > backKeyPressedTime + 2000) {
            backKeyPressedTime = System.currentTimeMillis()
            Toast.makeText(this, "\'뒤로\' 버튼을 한번 더 누르시면 종료됩니다.", Toast.LENGTH_SHORT).show()
            return
        }

        // 2초 이내에 뒤로가기 버튼을 한번 더 클릭시 finish()(앱 종료)
        if (System.currentTimeMillis() <= backKeyPressedTime + 2000) {
            finish()
        }
    }
}

