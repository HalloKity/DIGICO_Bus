package com.kt.digicobus.fragment.commute

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kt.digicobus.GOGenieApplication
import com.kt.digicobus.R
import com.kt.digicobus.adapter.TicketListAdapter
import com.kt.digicobus.data.data.Companion.allList
import com.kt.digicobus.data.data.Companion.commuteBusInfoList
import com.kt.digicobus.data.data.Companion.commuteLeaveBusInfoList
import com.kt.digicobus.data.model.CommuteBusInfo
import com.kt.digicobus.databinding.FragmentCommuteLeaveWorkBinding
import com.kt.digicobus.service.CommuteService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import me.everything.android.ui.overscroll.OverScrollDecoratorHelper

class CommuteLeaveWorkFragment : Fragment() {
    private lateinit var binding : FragmentCommuteLeaveWorkBinding
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
        binding = FragmentCommuteLeaveWorkBinding.inflate(layoutInflater)

        commuteBusInfoList.clear()

        CoroutineScope(Dispatchers.Main).launch {
            getAllCommuteBusInfo()
            setAdapter()
            search()
        }

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

                for (i in 0 until commuteLeaveBusInfoList.size) {
                    val data: CommuteBusInfo = commuteLeaveBusInfoList[i]

                    //???????????? ???????????? ?????? ??? ????????? ????????? (00???, ?????? ??????, ????????? ??????)
                    if (data.mainPlace.toLowerCase().contains(newText.toLowerCase())) {
                        filterList.add(data)
                    }else if(data.detailPlace.toLowerCase().contains(newText.toLowerCase())){
                        filterList.add(data)
                    }else if(data.line.toLowerCase().contains(newText.toLowerCase())){
                        filterList.add(data)
                    }
                }

                for(i in 0 until commuteLeaveBusInfoList.size){
                    commuteLeaveBusInfoList[i].isClick = false
                }
                binding.tvEndPlace  .text = ""

                val adapter = TicketListAdapter(ctx, null, R.layout.listview_ticket, filterList, binding)
                recyclerView.adapter = adapter

                return false
            }
        })
    }

    private fun setAdapter(){
        // RecyclerView ?????? ??????
        recyclerView = binding.recyclerview
        recyclerView.layoutManager = LinearLayoutManager(ctx, LinearLayoutManager.VERTICAL, false)
        OverScrollDecoratorHelper.setUpOverScroll(recyclerView, OverScrollDecoratorHelper.ORIENTATION_VERTICAL)

        // 2. Adapter ?????? ??????(??? ?????? ?????? ?????? ????????? Layout??? ????????? ??????)
        ticketListAdapter = TicketListAdapter(ctx, null, R.layout.listview_ticket, commuteLeaveBusInfoList, binding)

        // 3. RecyclerView??? Adapter ??????
        recyclerView.adapter = ticketListAdapter
    }

    private suspend fun getAllCommuteBusInfo() {
        withContext(Dispatchers.IO) {
            val service = GOGenieApplication.retrofit.create(CommuteService::class.java)
            val response = service.selectAllCommuteBusInfo().execute()

            if (response.code() == 200) {
                var resp = response.body()

                allList = (resp as List<CommuteBusInfo>).toMutableList()

                //???????????? ????????? ?????????
                commuteLeaveBusInfoList = mutableListOf()

                for(i in 0 until allList.size){
                    if(allList[i].commuteId.toInt() == 1){
                        commuteLeaveBusInfoList.add(allList[i])
                    }
                }

                Log.d(TAG, "getAllBusInfo: ${commuteLeaveBusInfoList.size}")
            } else {
                Log.d(TAG, "getAllBusInfo: error code")
            }
        }
    }

}