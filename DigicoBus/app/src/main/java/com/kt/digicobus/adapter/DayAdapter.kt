package com.kt.digicobus.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.kt.digicobus.R
import com.kt.digicobus.data.data.Companion.busRegisterList
import com.kt.digicobus.data.data.Companion.choiceRoute
import com.kt.digicobus.data.model.RemainSeat
import com.kt.digicobus.data.model.ReserveRegister
import com.kt.digicobus.data.model.ReserveSearch
import com.kt.digicobus.databinding.FragmentCommuteCalendarChoiceBinding
import kotlinx.android.synthetic.main.list_item_day.view.*
import kotlinx.android.synthetic.main.list_item_month.view.*
import java.text.SimpleDateFormat
import java.util.*

const val TAG = "DayAdapter"
class DayAdapter(var context:Context, var binding: FragmentCommuteCalendarChoiceBinding, val tempMonth:Int,
                 val commuteId: Int?,
                 val dayList: MutableList<Date>, var dayClickCheckList: MutableList<Boolean>, var remainSeatList: List<RemainSeat>,
                 val reservationInfoList: List<ReserveSearch>)
    : RecyclerView.Adapter<DayAdapter.DayView>() {

    val ROW = 6

    inner class DayView(val layout: View): RecyclerView.ViewHolder(layout)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DayView {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.list_item_day, parent, false)
        return DayView(view)
    }

    @SuppressLint("SimpleDateFormat")
    override fun onBindViewHolder(holder: DayView, position: Int) {
        holder.layout.item_day_layout.setOnClickListener {
            Toast.makeText(holder.layout.context, "${dayList[position]}", Toast.LENGTH_SHORT).show()
        }
        holder.layout.item_day_text.text = dayList[position].date.toString()

        //좌석 클릭시 배경 변환 -> 다중 선택 가능하게
        holder.layout.item_day_layout.setOnClickListener{
            //true
            if(dayClickCheckList[position]){
                dayClickCheckList[position] = false
                holder.layout.item_day_layout.setBackgroundColor(it.resources.getColor(R.color.white))
                //좌석 클릭시 예약 등록 리스트에서 빼기
                var date = "2022-${dayList[position].month+1}-${holder.layout.item_day_text.text}"
                for(i in 0 until busRegisterList.size){
                    if(date == busRegisterList[i].reserveDate){
                        busRegisterList.removeAt(i)
                        break;
                    }
                }
                println("------------buscancle-------------")
                for(i in busRegisterList.indices){
                    println(busRegisterList[i])
                }
            }
            //false
            else{
                dayClickCheckList[position] = true
                holder.layout.item_day_layout.setBackgroundColor(it.resources.getColor(R.color.mint))

                //좌석 클릭시 예약 등록 리스트에 넣기
                var date = "2022-${dayList[position].month+1}-${holder.layout.item_day_text.text}"
                busRegisterList.add(ReserveRegister(date,choiceRoute.busId.toInt(), choiceRoute.stationId.toInt()))
                println("------------busRegi-------------")
                for(i in busRegisterList.indices){
                    println(busRegisterList[i])
                }
            }


            //선택된 좌석이 1개 이상일 경우
            var seatChoiceCnt = 0
            for(i in 0 until dayClickCheckList.size){
                if(dayClickCheckList[i]){
                    seatChoiceCnt++
                }
            }

            binding.btnChoice.isEnabled = seatChoiceCnt > 0

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

        var date = Date()

        //잔여좌석 적용
        applyRemainingSeats(holder,date.month.toString())

        //오늘 날짜일 경우 백그라운드 동그라미 활성화 & 글자 색 화이트로 변경
        if(date.date == dayList[position].date && date.month == dayList[position].month){
            holder.layout.item_day_text.setTextColor(Color.WHITE)
            holder.layout.item_circle_day.visibility = View.VISIBLE
        }

        //이번달 지난날 , 지난 달 날짜
        if((date.date > dayList[position].date && date.month == dayList[position].month) ||
            (date.year > dayList[position].year && date.month > dayList[position].month) ){
            holder.layout.item_day_layout.isClickable = false
            holder.layout.tv_left_seat.text = ""
            holder.layout.tv_seok.text = ""
            holder.layout.item_day_text.alpha = 0.4f
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
                holder.layout.item_day_layout.isClickable = false
                holder.layout.tv_left_seat.text = ""
                holder.layout.tv_seok.text = ""
                holder.layout.item_day_text.alpha = 0.4f
            }
        }
        // 오늘이 25일 이후면 풀어주기 코드
        else if(date.date >= 25){
            //아직 구현 안함
        }

        //좌석 0개면 배경화면 및 좌석 색 회색으로 처리
        if(holder.layout.tv_left_seat.text == "0"){
            holder.layout.item_day_layout.isClickable = false
            holder.layout.item_day_layout.setBackgroundColor(context.resources.getColor(R.color.gray_100))
            holder.layout.tv_left_seat.setTextColor(Color.rgb(170,170,170))
            holder.layout.tv_seok.setTextColor(Color.rgb(170,170,170))
        }

        // 예약완료인 날짜
        val pattern = SimpleDateFormat("yyyy-MM-dd")
        val dateString = pattern.format(dayList[position])
        if(commuteId != null
            && reservationInfoList.any { it.reserveDate == dateString && it.commuteId == commuteId }) {
            holder.layout.item_day_layout.isClickable = false
            holder.layout.item_day_layout.setBackgroundResource(R.drawable.reserved_date_background)
        }
    }

    override fun getItemCount(): Int {
        return ROW * 5
    }

    //잔여좌석 적용
    fun applyRemainingSeats(holder: DayView, inputMonth:String){
        for(i in remainSeatList.indices){
            //"2022-12-12"
            var YMDList = remainSeatList[i].date!!.split("-")
            var year = YMDList[0]
            var month = YMDList[1]
            var day = YMDList[2]

            // 예약 월, 일이 같다면 전체 44석 - 예약된 좌석 갯수를 적용
            var newInputMonth = (inputMonth.toInt()+1).toString()
            if(newInputMonth == month && holder.layout.item_day_text.text.toString() == day){
                holder.layout.tv_left_seat.text = remainSeatList[i].remainSeatsCount.toString()
            }
        }
    }
}