package com.kt.digicobus.fragment.carpool

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.kt.digicobus.R
import com.kt.digicobus.databinding.FragmentCarPoolBinding
import com.kt.digicobus.databinding.FragmentCommuteBinding

class CarPoolFragment : Fragment() {
    private lateinit var binding : FragmentCarPoolBinding

    private lateinit var ctx: Context

    override fun onAttach(context: Context) {
        super.onAttach(context)
        ctx = context
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCarPoolBinding.inflate(layoutInflater)

        //운전자 탑승자 fragment 연결
        val navHostFragment =
            childFragmentManager.findFragmentById(R.id.nav_host_carpool) as NavHostFragment
        val navController = navHostFragment.navController
        NavigationUI.setupWithNavController(binding.tabCarpoolView, navController)

        return binding.root
    }

}