package com.kt.digicobus.fragment.commute

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kt.digicobus.R
import com.kt.digicobus.adapter.TicketHolder
import com.kt.digicobus.adapter.TicketListAdapter
import com.kt.digicobus.data.TicketContent
import com.kt.digicobus.data.data
import com.kt.digicobus.databinding.ActivityLoginBinding.inflate
import com.kt.digicobus.databinding.FragmentCommuteMainBinding
import me.everything.android.ui.overscroll.OverScrollDecoratorHelper


// 통근버스 1
class CommuteMainFragment : Fragment() {
    private lateinit var binding : FragmentCommuteMainBinding

    private lateinit var ctx: Context

    override fun onAttach(context: Context) {
        super.onAttach(context)
        ctx = context
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCommuteMainBinding.inflate(layoutInflater)

        //출퇴근 fragment 연결
        val navHostFragment =
            childFragmentManager.findFragmentById(R.id.nav_host) as NavHostFragment
        val navController = navHostFragment.navController
        NavigationUI.setupWithNavController(binding.topNavigationView, navController)

        return binding.root
    }
}