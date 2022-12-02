package com.kt.digicobus.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.kt.digicobus.R
import kotlinx.android.synthetic.main.list_item_day.view.*
import java.util.*


class DayAdapter(val tempMonth:Int, val dayList: MutableList<Date>): RecyclerView.Adapter<DayAdapter.DayView>() {
    val ROW = 6

    inner class DayView(val layout: View): RecyclerView.ViewHolder(layout)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DayView {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.list_item_day, parent, false)
        return DayView(view)
    }

    override fun onBindViewHolder(holder: DayView, position: Int) {
        holder.layout.item_day_layout.setOnClickListener {
            Toast.makeText(holder.layout.context, "${dayList[position]}", Toast.LENGTH_SHORT).show()
        }
        holder.layout.item_day_text.text = dayList[position].date.toString()

        //좌석 0개면 배경화면 및 좌석 색 회색으로 처리
        if(holder.layout.tv_left_seat.text == "0"){
            holder.layout.item_day_layout.setBackgroundColor(Color.argb(70,170,170,170))
            holder.layout.tv_left_seat.setTextColor(Color.rgb(170,170,170))
            holder.layout.tv_middle.setTextColor(Color.rgb(170,170,170))
            holder.layout.tv_total_seat.setTextColor(Color.rgb(170,170,170))
        }

        //좌석 클릭시 배경 변환
        holder.layout.item_day_layout.setOnClickListener{
            // 이전에 선택된 좌석말고 다른 곳 클릭 시 이전 곳 배경 다시 흰색으로 변환하고 선택한 곳 색깔 변환


            //좌석 없을 경우 배경 변환 없음
            if(holder.layout.tv_left_seat.text != "0"){
                holder.layout.item_day_layout.setBackgroundColor(it.resources.getColor(R.color.mint))
            }
        }

        //전월, 다음월 일 경우 날짜 색 회색 처리
        if(tempMonth != dayList[position].month) {
            holder.layout.item_day_text.alpha = 0.4f
        }
    }

    override fun getItemCount(): Int {
        return ROW * 5
    }
}