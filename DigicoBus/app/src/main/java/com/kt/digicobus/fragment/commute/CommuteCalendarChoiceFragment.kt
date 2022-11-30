package com.kt.digicobus.fragment.commute

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.kt.digicobus.R
import com.kt.digicobus.databinding.FragmentCommuteBinding
import com.kt.digicobus.databinding.FragmentCommuteBusChoiceBinding
import com.kt.digicobus.databinding.FragmentCommuteCalendarChoiceBinding

//통근버스 3
class CommuteCalendarChoiceFragment : Fragment() {
    private lateinit var binding : FragmentCommuteCalendarChoiceBinding

    private lateinit var ctx: Context

    override fun onAttach(context: Context) {
        super.onAttach(context)
        ctx = context
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCommuteCalendarChoiceBinding.inflate(layoutInflater)

        return binding.root
    }

}