package com.kt.digicobus.fragment.commute

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kt.digicobus.R
import com.kt.digicobus.adapter.TicketListAdapter
import com.kt.digicobus.data.TicketContent
import com.kt.digicobus.data.data
import com.kt.digicobus.data.data.Companion.ticketList
import com.kt.digicobus.databinding.FragmentCommuteGoToWorkBinding
import me.everything.android.ui.overscroll.OverScrollDecoratorHelper
import java.util.*


class CommuteGoToWorkFragment : Fragment() {
    private lateinit var binding : FragmentCommuteGoToWorkBinding
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
        binding = FragmentCommuteGoToWorkBinding.inflate(layoutInflater)
//        binding.btnChoice.setOnClickListener {
//            container?.findNavController()?.navigate(R.id.action_CommuteMainFragment_to_CommuteCalendarChoiceFragment)
////            val transaction: FragmentTransaction = childFragmentManager.beginTransaction()
////            val fragment1 = CommuteCalendarChoiceFragment()
////            transaction.replace(R.id.fragment, fragment1)
////            transaction.commit()
////            it.findNavController().navigate(R.id.action_CommuteMainFragment_to_CommuteCalendarChoiceFragment)
//        }

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
                val filterList: ArrayList<TicketContent> = ArrayList()

                for (i in 0 until ticketList.size) {
                    val data: TicketContent = ticketList.get(i)

                    //데이터와 비교해서 내가 쓴 단어가 있다면 (00행, 메인 장소, 디테일 장소)
                    if (data.mainPlace.toLowerCase().contains(newText.toLowerCase())) {
                        filterList.add(data)
                    }else if(data.detailPlace.toLowerCase().contains(newText.toLowerCase())){
                        filterList.add(data)
                    }else if(data.line.toLowerCase().contains(newText.toLowerCase())){
                        filterList.add(data)
                    }
                }

                val adapter = TicketListAdapter(ctx, binding, com.kt.digicobus.R.layout.listview_ticket, filterList)
                recyclerView.adapter = adapter

                return false
            }
        })
    }

    private fun setAdapter(){
        fillData()

        // RecyclerView 객체 생성
        recyclerView = binding.recyclerview
        recyclerView.layoutManager = LinearLayoutManager(ctx, LinearLayoutManager.VERTICAL, false)
        OverScrollDecoratorHelper.setUpOverScroll(recyclerView, OverScrollDecoratorHelper.ORIENTATION_VERTICAL)

        // 2. Adapter 객체 생성(한 행을 위해 반복 생성할 Layout과 데이터 전달)
        ticketListAdapter = TicketListAdapter(ctx, binding, R.layout.listview_ticket, ticketList)

        // 3. RecyclerView와 Adapter 연결
        recyclerView.adapter = ticketListAdapter
    }

    fun fillData(){
        data.ticketList.clear()
        data.ticketList.add(TicketContent("인천","간석오거리역","1번출구 버스정류장 앞","6:50",true,false))
        data.ticketList.add(TicketContent("인천","부평역","1번출구 큰사거리 고은성모의원 앞","7:00",false,false))
        data.ticketList.add(TicketContent("인천","송내남부역","커피에 반하다 앞","7:15",false,false))
        data.ticketList.add(TicketContent("인천","시흥영업소","판교 방향","7:25",false,false))
        data.ticketList.add(TicketContent("창동/잠실","창동역","1번출구 프레스티지 앞","6:55",false,false))
        data.ticketList.add(TicketContent("창동/잠실","태릉입구역","2번출구","7:15",false,false))
        data.ticketList.add(TicketContent("창동/잠실","잠실역","6번출구 앞 버스정류장 앞","7:50",false,false))
        data.ticketList.add(TicketContent("목동","목동사옥","세신비젼프라자앞","7:10",false,false))
        data.ticketList.add(TicketContent("목동","오목교역","2번출구","7:13",false,false))
        data.ticketList.add(TicketContent("신도림","신도림역","6번출구","7:10",false,false))
        data.ticketList.add(TicketContent("신도림","마포역","3번출구","7:25",false,false))
        data.ticketList.add(TicketContent("신도림","서울역","14번출구","7:45",false,false))
    }

}