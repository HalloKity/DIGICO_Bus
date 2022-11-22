package com.kt.digicobus.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kt.digicobus.R
import com.kt.digicobus.databinding.FragmentCommuteBinding
import com.kt.digicobus.databinding.FragmentMoreBinding
import com.kt.digicobus.databinding.FragmentReservationConfirmBinding

class ReservationConfirmFragment : Fragment() {
    private lateinit var binding : FragmentReservationConfirmBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentReservationConfirmBinding.inflate(layoutInflater)

        return binding.root
    }
}