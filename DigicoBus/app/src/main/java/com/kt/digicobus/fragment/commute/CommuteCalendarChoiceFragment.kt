package com.kt.digicobus.fragment.commute

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import com.kt.digicobus.R
import com.kt.digicobus.adapter.MonthAdapter
import com.kt.digicobus.databinding.FragmentCommuteBinding
import com.kt.digicobus.databinding.FragmentCommuteBusChoiceBinding
import com.kt.digicobus.databinding.FragmentCommuteCalendarChoiceBinding

//통근버스 3
class CommuteCalendarChoiceFragment : Fragment() {
    private lateinit var binding : FragmentCommuteCalendarChoiceBinding

    private lateinit var ctx: Context

    override fun onAttach(context: Context) {
        super.onAttach(context)
        ctx = context
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCommuteCalendarChoiceBinding.inflate(layoutInflater)

        //캘린더 뼈대
        makeCalendar()

        return binding.root
    }

    private fun makeCalendar() {
        val monthListManager = LinearLayoutManager(ctx, LinearLayoutManager.HORIZONTAL, false)
        val monthListAdapter = MonthAdapter()

        binding.calendarCustom.apply {
            layoutManager = monthListManager
            adapter = monthListAdapter
            // scrollToPosition은 리스트를 item의 위치를 지정한 곳에서 시작한다.
            // 해당 위치에서 리스트를 시작하는 이유는 뒤 Adapter 부분에서 설명한다.
            scrollToPosition(Int.MAX_VALUE/2)
        }
        // PagerSnapHelper()를 설정함으로써 한 항목씩 스크롤이 되도록 만들 수 있다.
        val snap = PagerSnapHelper()
        snap.attachToRecyclerView(binding.calendarCustom)
    }


}