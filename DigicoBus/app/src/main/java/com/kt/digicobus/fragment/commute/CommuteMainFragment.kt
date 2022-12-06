package com.kt.digicobus.fragment.commute

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
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
    private lateinit var recyclerView: RecyclerView
    private lateinit var ticketListAdapter: TicketListAdapter

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

        val items = resources.getStringArray(R.array.go_to_work_and_home_array)
        val myAdapter = ArrayAdapter(ctx, android.R.layout.simple_spinner_dropdown_item, items)
        binding.spinner.adapter = myAdapter

        setAdapter()

        return binding.root
    }

    private fun setAdapter(){
        fillData()

        // RecyclerView 객체 생성
        recyclerView = binding.recyclerview
        recyclerView.layoutManager = LinearLayoutManager(ctx, LinearLayoutManager.VERTICAL, false)
        OverScrollDecoratorHelper.setUpOverScroll(recyclerView, OverScrollDecoratorHelper.ORIENTATION_VERTICAL)

        // 2. Adapter 객체 생성(한 행을 위해 반복 생성할 Layout과 데이터 전달)
        ticketListAdapter = TicketListAdapter(ctx, R.layout.listview_ticket, data.ticketList)

        // 3. RecyclerView와 Adapter 연결
        recyclerView.adapter = ticketListAdapter
    }

    fun fillData(){
        data.ticketList.clear()
        data.ticketList.add(TicketContent("인천","남동구>부평구>부천>시흥","간선오거리역 1번출구","06:30","판교 사옥","08:30",false))
        data.ticketList.add(TicketContent("목동","목동>오목교역","목동사옥 세신비젼프라자앞","07:10","판교 사옥","08:40",false))
        data.ticketList.add(TicketContent("강북","창동>태릉입구>잠실","창동역 1번출구 프레스티지 앞","06:30","판교 사옥","08:30",false))
        data.ticketList.add(TicketContent("신도림","신도림>마포>서울역","신도림역","07:10","판교 사옥","08:40",false))
        data.ticketList.add(TicketContent("사당","사당역","사당역 1번출구 옆 공용주차장","06:30","판교 사옥","08:30",false))
        data.ticketList.add(TicketContent("양재","양재역","양재역 5번출구","07:10","판교 사옥","08:40",false))
        data.ticketList.add(TicketContent("대전","유성온천역>온천교>궁동","유성온천역 3번출구","06:30","판교 사옥","08:30",false))
        data.ticketList.add(TicketContent("용인","보정>죽전>오리>미금>정자","보정역 1번출구","07:10","판교 사옥","08:40",false))
        data.ticketList.add(TicketContent("부산","해운대>광안리>송정","부산역 12번 출구","06:30","판교 사옥","08:30",false))
        data.ticketList.add(TicketContent("홍대","홍대입구","홍대입구역 3번출구","07:10","판교 사옥","08:40",false))
    }
}