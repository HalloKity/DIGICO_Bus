package com.kt.digicobus.adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.kt.digicobus.R
import com.kt.digicobus.data.data
import com.kt.digicobus.databinding.FragmentCommuteCalendarChoiceBinding
import kotlinx.android.synthetic.main.list_item_day.view.*
import java.util.*


class DayAdapter(var context:Context, var binding: FragmentCommuteCalendarChoiceBinding, val tempMonth:Int, val dayList: MutableList<Date>, var dayClickCheckList: MutableList<Boolean>)
    : RecyclerView.Adapter<DayAdapter.DayView>() {

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
            holder.layout.tv_seok.setTextColor(Color.rgb(170,170,170))
        }

        //좌석 클릭시 배경 변환
        holder.layout.item_day_layout.setOnClickListener{
            // 이전에 선택된 좌석말고 다른 곳 클릭 시 이전 곳 배경 다시 흰색으로 변환하고 선택한 곳 색깔 변환
            dayClickCheckList[position] = true
            holder.layout.item_day_layout.setBackgroundColor(it.resources.getColor(R.color.mint))

            for(i in 0 until dayClickCheckList.size){
                if(i != position){
                    dayClickCheckList[i]= false
                }
            }
            binding.btnChoice.isEnabled = true

            notifyDataSetChanged()
        }

        if(!dayClickCheckList[position]){
            holder.layout.item_day_layout.setBackgroundColor(context.resources.getColor(R.color.white))
        }else if(dayClickCheckList[position]){
            holder.layout.item_day_layout.setBackgroundColor(context.resources.getColor(R.color.mint))
        }


        //전월, 다음월 일 경우 날짜 색 회색 처리
        if(tempMonth != dayList[position].month) {
            holder.layout.item_day_text.alpha = 0.4f
        }

        //오늘 날짜일 경우 백그라운드 동그라미 활성화 & 글자 색 화이트로 변경
        var date = Date()
        if(date.date == dayList[position].date && date.month == dayList[position].month){
            holder.layout.item_day_text.setTextColor(Color.WHITE)
            holder.layout.item_circle_day.visibility = View.VISIBLE
        }

        //지난 날짜인 경우 마감처리

        //이번달 지난날 , 지난 달 날짜
        if((date.date > dayList[position].date && date.month == dayList[position].month) ||
            (date.year > dayList[position].year && date.month > dayList[position].month) ){
            holder.layout.item_day_layout.setBackgroundColor(context.resources.getColor(R.color.gray_70))
            holder.layout.item_day_layout.isClickable = false
            holder.layout.tv_left_seat.text = ""
            holder.layout.tv_seok.text = ""
        }

        //오늘이 25일 이전이면
        if(date.date < 25){
            // 다음달 오픈 x
            // 이번달 25일 이후 또는 다음달 이상
            if(
                date.year < dayList[position].year ||
                (date.year == dayList[position].year && date.month < dayList[position].month) ||
                (date.year == dayList[position].year && date.month > dayList[position].month)
            ){
                holder.layout.item_day_layout.setBackgroundColor(context.resources.getColor(R.color.gray_70))
                holder.layout.item_day_layout.isClickable = false
                holder.layout.tv_left_seat.text = ""
                holder.layout.tv_seok.text = ""
            }
        }
        // 오늘이 25일 이후면 풀어주기 코드
        else if(date.date >= 25){
            //아직 구현 안함
        }
    }

    override fun getItemCount(): Int {
        return ROW * 5
    }
}