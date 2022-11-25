package com.kt.digicobus.fragment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kt.digicobus.R
import com.kt.digicobus.adapter.MoreListAdapter
import com.kt.digicobus.adapter.TicketListAdapter
import com.kt.digicobus.data.TicketContent
import com.kt.digicobus.data.data.Companion.ticketList
import com.kt.digicobus.databinding.FragmentCommuteBinding
import me.everything.android.ui.overscroll.OverScrollDecoratorHelper

class CommuteFragment : Fragment() {
    private lateinit var binding : FragmentCommuteBinding
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
        binding = FragmentCommuteBinding.inflate(layoutInflater)

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
        ticketListAdapter = TicketListAdapter(ctx, R.layout.listview_ticket, ticketList )

        // 3. RecyclerView와 Adapter 연결
        recyclerView.adapter = ticketListAdapter
    }

    fun fillData(){
        ticketList.clear()
        ticketList.add(TicketContent("인천","남동구/인천 부평구/부천/시흥","간선오거리역 1번출구","06:30","판교 사옥","08:30"))
        ticketList.add(TicketContent("목동","목동/오목교역","목동사옥 세신비젼프라자앞","07:10","판교 사옥","08:40"))
        ticketList.add(TicketContent("인천","남동구/인천 부평구/부천/시흥","간선오거리역 1번출구","06:30","판교 사옥","08:30"))
        ticketList.add(TicketContent("목동","목동/오목교역","목동사옥 세신비젼프라자앞","07:10","판교 사옥","08:40"))
        ticketList.add(TicketContent("인천","남동구/인천 부평구/부천/시흥","간선오거리역 1번출구","06:30","판교 사옥","08:30"))
        ticketList.add(TicketContent("목동","목동/오목교역","목동사옥 세신비젼프라자앞","07:10","판교 사옥","08:40"))
        ticketList.add(TicketContent("인천","남동구/인천 부평구/부천/시흥","간선오거리역 1번출구","06:30","판교 사옥","08:30"))
        ticketList.add(TicketContent("목동","목동/오목교역","목동사옥 세신비젼프라자앞","07:10","판교 사옥","08:40"))
        ticketList.add(TicketContent("인천","남동구/인천 부평구/부천/시흥","간선오거리역 1번출구","06:30","판교 사옥","08:30"))
        ticketList.add(TicketContent("목동","목동/오목교역","목동사옥 세신비젼프라자앞","07:10","판교 사옥","08:40"))
    }
}