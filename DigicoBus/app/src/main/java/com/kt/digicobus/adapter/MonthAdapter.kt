package com.kt.digicobus.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kt.digicobus.R
import com.kt.digicobus.data.data
import com.kt.digicobus.databinding.FragmentCommuteCalendarChoiceBinding
import kotlinx.android.synthetic.main.list_item_day.view.*
import kotlinx.android.synthetic.main.list_item_month.view.*
import java.util.*

class MonthAdapter(var context: Context,var binding: FragmentCommuteCalendarChoiceBinding): RecyclerView.Adapter<MonthAdapter.MonthView>() {
    val center = Int.MAX_VALUE / 2
    private var calendar = Calendar.getInstance()

    inner class MonthView(val layout: View): RecyclerView.ViewHolder(layout)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MonthView {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item_month, parent, false)
        return MonthView(view)
    }

    override fun onBindViewHolder(holder: MonthView, position: Int) {
        //현재 날짜로 초기화
        calendar.time = Date()
        //월의 1일로 이동
        calendar.set(Calendar.DAY_OF_MONTH, 1)
        //add로 월단위로 'position-center'만큼 이동
        calendar.add(Calendar.MONTH, position - center)
        holder.layout.item_month_text.text = "${calendar.get(Calendar.YEAR)}년 ${calendar.get(Calendar.MONTH) + 1}월"
        //현재월 저장
        val tempMonth = calendar.get(Calendar.MONTH)

        var dayList: MutableList<Date> = MutableList(6*7){ Date() }

        //필요한 모든 일 수 받기
        for(i in 0..5) {
            for(k in 0..6) {
                calendar.add(Calendar.DAY_OF_MONTH, (1-calendar.get(Calendar.DAY_OF_WEEK)) + k)
                dayList[i * 7 + k] = calendar.time
            }
            calendar.add(Calendar.WEEK_OF_MONTH, 1)
        }

        // 토요일과 일요일 빼고 다시 리스트에 날짜 저장
        var dayListNoSatSun: MutableList<Date> = mutableListOf()
        for(i in 0 until dayList.count()){
            //일요일과 토요일이 아닐때 리스트에 넣기
            if(i%7 != 0 && i%7 != 6){
                dayListNoSatSun.add(dayList[i])
            }
        }

        var dayClickCheckList = MutableList(dayListNoSatSun.size, { i -> false})

        val dayListManager = GridLayoutManager(holder.layout.context, 5)
        //주말없는 일수를 어댑터에 적용
        val dayListAdapter = DayAdapter(context, binding, tempMonth, dayListNoSatSun,dayClickCheckList)

        holder.layout.item_month_day_list.apply {
            layoutManager = dayListManager
            adapter = dayListAdapter
        }

        //지난달 보여주기
        holder.layout.item_left_arrow.setOnClickListener{

        }

        //다음달 보여주기
        holder.layout.item_right_arrow.setOnClickListener{

        }


    }

    override fun getItemCount(): Int {
        return Int.MAX_VALUE
    }
}