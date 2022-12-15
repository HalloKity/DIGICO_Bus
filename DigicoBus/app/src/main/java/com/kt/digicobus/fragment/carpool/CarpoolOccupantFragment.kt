package com.kt.digicobus.fragment.carpool

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kt.digicobus.R
import com.kt.digicobus.databinding.FragmentCarpoolDriverBinding
import com.kt.digicobus.databinding.FragmentCarpoolOccupantBinding


class CarpoolOccupantFragment : Fragment() {
    private lateinit var binding : FragmentCarpoolOccupantBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCarpoolOccupantBinding.inflate(layoutInflater)

        return binding.root
    }
}