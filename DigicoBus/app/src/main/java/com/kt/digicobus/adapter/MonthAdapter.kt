package com.kt.digicobus.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kt.digicobus.R
import kotlinx.android.synthetic.main.list_item_month.view.*
import java.util.*

class MonthAdapter: RecyclerView.Adapter<MonthAdapter.MonthView>() {
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

        for(i in 0..5) {
            for(k in 0..6) {
                calendar.add(Calendar.DAY_OF_MONTH, (1-calendar.get(Calendar.DAY_OF_WEEK)) + k)
                dayList[i * 7 + k] = calendar.time
            }
            calendar.add(Calendar.WEEK_OF_MONTH, 1)
        }

        var dayListNoWeek: MutableList<Date> = mutableListOf()
        println("dayWithWeek : ${dayList.size}")
        for(i in 0 until dayList.count()){
            if(i%7 != 0 && i%7 != 6){
                dayListNoWeek.add(dayList[i])
            }
        }
        println("dayNoWeek : ${dayListNoWeek.size}")


        val dayListManager = GridLayoutManager(holder.layout.context, 5)
        val dayListAdapter = DayAdapter(tempMonth, dayListNoWeek)

        holder.layout.item_month_day_list.apply {
            layoutManager = dayListManager
            adapter = dayListAdapter
        }
    }

    override fun getItemCount(): Int {
        return Int.MAX_VALUE
    }
}