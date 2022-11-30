package com.kt.digicobus.fragment.commute

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kt.digicobus.R
import com.kt.digicobus.adapter.BusStopListAdapter
import com.kt.digicobus.adapter.TicketListAdapter
import com.kt.digicobus.data.BusStopContent
import com.kt.digicobus.data.TicketContent
import com.kt.digicobus.data.data
import com.kt.digicobus.data.data.Companion.busStopList
import com.kt.digicobus.databinding.FragmentCommuteBinding
import com.kt.digicobus.databinding.FragmentCommuteBusChoiceBinding
import me.everything.android.ui.overscroll.OverScrollDecoratorHelper

//통근버스 2
class CommuteBusChoiceFragment : Fragment() {
    private lateinit var binding : FragmentCommuteBusChoiceBinding
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
        binding = FragmentCommuteBusChoiceBinding.inflate(layoutInflater)

//        val items = resources.getStringArray(R.array.go_to_work_and_home_array)
//        val myAdapter = ArrayAdapter(ctx, android.R.layout.simple_spinner_dropdown_item, items)
//        binding.spinner.adapter = myAdapter

        setAdapter()

        //뒤로가기
        binding.btnBack.setOnClickListener{
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
        busStopListAdapter = BusStopListAdapter(ctx, R.layout.listview_detail_bus_info, busStopList)

        // 3. RecyclerView와 Adapter 연결
        recyclerView.adapter = busStopListAdapter
    }

    fun fillData(){
        busStopList.clear()
        busStopList.add(BusStopContent("간선오거리역 1번출구\n버스정류장 앞","(06:50)"))
        busStopList.add(BusStopContent("부평역 1번출구 맞은편\n큰거리 고은성모의원앞","(07:00)"))
        busStopList.add(BusStopContent("숙대입구역 1번출구\n버스정류장 앞","(06:50)"))
        busStopList.add(BusStopContent("안국역 3번출구\n버스정류장 앞","(06:50)"))
        busStopList.add(BusStopContent("정자역 7번출구\n버스정류장 앞","(06:50)"))
        busStopList.add(BusStopContent("부평역 3번출구\n버스정류장 앞","(06:50)"))
        busStopList.add(BusStopContent("정자역 2번출구\n버스정류장 앞","(06:50)"))
        busStopList.add(BusStopContent("간선오거리역 1번출구\n버스정류장 앞","(06:50)"))
        busStopList.add(BusStopContent("간석육거리역 3번출구\n버스정류장 앞","(06:50)"))
        busStopList.add(BusStopContent("간선칠거리역 4번출구\n버스정류장 앞","(06:50)"))

    }
}