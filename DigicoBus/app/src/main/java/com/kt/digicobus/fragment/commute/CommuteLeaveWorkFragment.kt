package com.kt.digicobus.fragment.commute

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kt.digicobus.R
import com.kt.digicobus.adapter.LeaveTicketListAdapter
import com.kt.digicobus.adapter.TicketListAdapter
import com.kt.digicobus.data.data
import com.kt.digicobus.data.data.Companion.commuteBusInfoList
import com.kt.digicobus.data.model.CommuteBusInfo
import com.kt.digicobus.databinding.FragmentCommuteGoToWorkBinding
import com.kt.digicobus.databinding.FragmentCommuteLeaveWorkBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import me.everything.android.ui.overscroll.OverScrollDecoratorHelper

class CommuteLeaveWorkFragment : Fragment() {
    private lateinit var binding : FragmentCommuteLeaveWorkBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var ticketListAdapter: LeaveTicketListAdapter
    private lateinit var goToHomeRouteList: MutableList<CommuteBusInfo>

    private lateinit var ctx: Context

    override fun onAttach(context: Context) {
        super.onAttach(context)
        ctx = context
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCommuteLeaveWorkBinding.inflate(layoutInflater)

        goToHomeRouteList = mutableListOf()
        for(i in 0 until commuteBusInfoList.size){
            if(commuteBusInfoList[i].commuteId.toInt() == 1){
                goToHomeRouteList.add(commuteBusInfoList[i])
            }
        }

        setAdapter()
        search()

        return binding.root
    }
    fun search(){
        val searchView = binding.btnSearch
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                val filterList = mutableListOf<CommuteBusInfo>()

                for (i in 0 until goToHomeRouteList.size) {
                    val data: CommuteBusInfo = goToHomeRouteList[i]

                    //데이터와 비교해서 내가 쓴 단어가 있다면 (00행, 메인 장소, 디테일 장소)
                    if (data.mainPlace.toLowerCase().contains(newText.toLowerCase())) {
                        filterList.add(data)
                    }else if(data.detailPlace.toLowerCase().contains(newText.toLowerCase())){
                        filterList.add(data)
                    }else if(data.line.toLowerCase().contains(newText.toLowerCase())){
                        filterList.add(data)
                    }
                }

                val adapter = LeaveTicketListAdapter(ctx, binding, R.layout.listview_ticket, filterList)
                recyclerView.adapter = adapter

                return false
            }
        })
    }

    private fun setAdapter(){
        // RecyclerView 객체 생성
        recyclerView = binding.recyclerview
        recyclerView.layoutManager = LinearLayoutManager(ctx, LinearLayoutManager.VERTICAL, false)
        OverScrollDecoratorHelper.setUpOverScroll(recyclerView, OverScrollDecoratorHelper.ORIENTATION_VERTICAL)

        // 2. Adapter 객체 생성(한 행을 위해 반복 생성할 Layout과 데이터 전달)
        ticketListAdapter = LeaveTicketListAdapter(ctx, binding, R.layout.listview_ticket, goToHomeRouteList)

        // 3. RecyclerView와 Adapter 연결
        recyclerView.adapter = ticketListAdapter
    }

}