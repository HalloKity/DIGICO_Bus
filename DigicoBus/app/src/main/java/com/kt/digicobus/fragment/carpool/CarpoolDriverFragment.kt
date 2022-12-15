package com.kt.digicobus.fragment.carpool

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kt.digicobus.R
import com.kt.digicobus.databinding.FragmentCarPoolBinding
import com.kt.digicobus.databinding.FragmentCarpoolDriverBinding


class CarpoolDriverFragment : Fragment() {
    private lateinit var binding : FragmentCarpoolDriverBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCarpoolDriverBinding.inflate(layoutInflater)

        return binding.root
    }
}