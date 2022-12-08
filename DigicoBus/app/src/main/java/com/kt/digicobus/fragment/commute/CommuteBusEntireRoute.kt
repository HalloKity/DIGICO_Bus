package com.kt.digicobus.fragment.commute

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kt.digicobus.R
import com.kt.digicobus.adapter.BusStopListAdapter
import com.kt.digicobus.data.BusStopContent
import com.kt.digicobus.data.data
import com.kt.digicobus.data.data.Companion.busStopList
import com.kt.digicobus.databinding.FragmentCommuteBusEntireRouteBinding
import me.everything.android.ui.overscroll.OverScrollDecoratorHelper

//통근버스 - 전체 노선 보기
class CommuteBusEntireRoute : Fragment() {
    private lateinit var binding : FragmentCommuteBusEntireRouteBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var busStopListAdapter: BusStopListAdapter

    private lateinit var ctx: Context

    override fun onAttach(context: Context) {
        super.onAttach(context)
        ctx = context
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCommuteBusEntireRouteBinding.inflate(layoutInflater)

        setAdapter()

        //뒤로가기
        binding.btnBack.setOnClickListener{
            data.busChoiceInfo = -1
            container?.findNavController()?.navigate(R.id.action_CommuteBusChoiceFragment_to_CommuteMainFragment)
        }

        return binding.root
    }

    private fun setAdapter(){
        fillData()

        // RecyclerView 객체 생성
        recyclerView = binding.recyclerview
        recyclerView.layoutManager = LinearLayoutManager(ctx, LinearLayoutManager.VERTICAL, false)
        OverScrollDecoratorHelper.setUpOverScroll(recyclerView, OverScrollDecoratorHelper.ORIENTATION_VERTICAL)

        // 2. Adapter 객체 생성(한 행을 위해 반복 생성할 Layout과 데이터 전달)
        busStopListAdapter = BusStopListAdapter(ctx,binding, R.layout.listview_detail_bus_info, busStopList)

        // 3. RecyclerView와 Adapter 연결
        recyclerView.adapter = busStopListAdapter
    }

    fun fillData(){
        busStopList.clear()
        busStopList.add(BusStopContent("간선오거리역","06:50", 37.469526, 126.708172,false))
        busStopList.add(BusStopContent("부평역","07:00", 37.488875, 126.723969,false))
        busStopList.add(BusStopContent("숙대입구역","06:50", 37.546516, 126.972094,false))
        busStopList.add(BusStopContent("안국역","06:50", 37.577335, 126.986010,false))
        busStopList.add(BusStopContent("정자역","06:50", 37.366767, 127.108334,false))
        busStopList.add(BusStopContent("부평역","06:50", 37.488875, 126.723969,false))
        busStopList.add(BusStopContent("정자역","06:50", 37.366767, 127.108334,false))
        busStopList.add(BusStopContent("간선오거리역","06:50", 37.469526, 126.708172,false))
        busStopList.add(BusStopContent("간석육거리역","06:50", 37.469526, 126.708172,false))
        busStopList.add(BusStopContent("판교사옥","08:45", 37.407385, 127.090450,false))

    }
}