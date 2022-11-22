package com.kt.digicobus.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kt.digicobus.R
import com.kt.digicobus.databinding.FragmentCommuteBinding

class CommuteFragment : Fragment() {
    private lateinit var binding : FragmentCommuteBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCommuteBinding.inflate(layoutInflater)

        return binding.root
    }

}