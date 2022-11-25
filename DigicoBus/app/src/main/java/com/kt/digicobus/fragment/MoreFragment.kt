package com.kt.digicobus.fragment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kt.digicobus.R
import com.kt.digicobus.adapter.MoreListAdapter
import com.kt.digicobus.data.data
import com.kt.digicobus.data.data.Companion.icon_list
import com.kt.digicobus.data.data.Companion.icon_name_list
import com.kt.digicobus.databinding.FragmentMoreBinding
import me.everything.android.ui.overscroll.OverScrollDecoratorHelper

class MoreFragment : Fragment() {
    private lateinit var binding : FragmentMoreBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var moreListAdapter: MoreListAdapter

    private lateinit var ctx: Context

    override fun onAttach(context: Context) {
        super.onAttach(context)
        ctx = context
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMoreBinding.inflate(layoutInflater)

        setAdapter()

        return binding.root
    }

    private fun setAdapter(){
        // RecyclerView 객체 생성
        recyclerView = binding.recyclerview
        recyclerView.layoutManager = LinearLayoutManager(ctx, LinearLayoutManager.VERTICAL, false)
        OverScrollDecoratorHelper.setUpOverScroll(recyclerView, OverScrollDecoratorHelper.ORIENTATION_HORIZONTAL)

        // 2. Adapter 객체 생성(한 행을 위해 반복 생성할 Layout과 데이터 전달)
        moreListAdapter = MoreListAdapter(ctx, R.layout.listview_more, icon_list, icon_name_list)

        // 3. RecyclerView와 Adapter 연결
        recyclerView.adapter = moreListAdapter
    }
}