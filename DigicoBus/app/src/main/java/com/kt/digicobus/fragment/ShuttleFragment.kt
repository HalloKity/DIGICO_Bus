package com.kt.digicobus.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kt.digicobus.R
import com.kt.digicobus.databinding.FragmentMoreBinding
import com.kt.digicobus.databinding.FragmentReservationConfirmBinding
import com.kt.digicobus.databinding.FragmentShuttleBinding

class ShuttleFragment : Fragment() {
    private lateinit var binding : FragmentShuttleBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentShuttleBinding.inflate(layoutInflater)

        return binding.root
    }

}