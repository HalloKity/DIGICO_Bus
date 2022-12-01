package com.kt.digicobus.fragment.shuttle

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.kt.digicobus.R
import com.kt.digicobus.databinding.FragmentShuttleBinding
import me.everything.android.ui.overscroll.OverScrollDecoratorHelper


class ShuttleFragment : Fragment() {
    private lateinit var binding : FragmentShuttleBinding
    private lateinit var ctx: Context

    override fun onAttach(context: Context) {
        super.onAttach(context)
        ctx = context
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentShuttleBinding.inflate(layoutInflater)

        //spinner
        val items = resources.getStringArray(R.array.shuttle_spinner_array)
        val myAdapter = ArrayAdapter(ctx, android.R.layout.simple_spinner_dropdown_item, items)
        binding.spinner.adapter = myAdapter

        //출퇴근 fragment 연결
        val navHostFragment = childFragmentManager.findFragmentById(R.id.nav_host) as NavHostFragment
        val navController = navHostFragment.navController
//        OverScrollDecoratorHelper.setUpOverScroll(R.id.nav_host, OverScrollDecoratorHelper.ORIENTATION_VERTICAL)
        NavigationUI.setupWithNavController(binding.tabView, navController)

        return binding.root
    }

}