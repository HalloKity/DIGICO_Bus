package com.kt.digicobus.fragment.commute

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.kt.digicobus.R
import com.kt.digicobus.databinding.FragmentCommuteBinding

// 통근버스 들어갔을 시 제어하는 첫 화면
class CommuteFragment : Fragment() {
    private lateinit var binding : FragmentCommuteBinding

    private lateinit var ctx: Context

    override fun onAttach(context: Context) {
        super.onAttach(context)
        ctx = context
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCommuteBinding.inflate(layoutInflater)

        //commutefragment에 네임 직접지정하면 화면 전환이 잘 안됨 이부분 해결책 찾기
        view?.findNavController()?.navigate(R.id.fragment)

        return binding.root
    }
}