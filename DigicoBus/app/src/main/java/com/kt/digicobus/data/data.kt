package com.kt.digicobus.data

import android.widget.TextView
import com.kt.digicobus.R

data class TicketContent(
    var tv_large_place: String,
    var tv_small_place: String,
    var tv_start_place_name:String,
    var tv_start_time: String,
    var tv_end_place_name: String,
    var tv_end_time: String,
)

class data{
    companion object{
        //MoreFragment
        val icon_list = mutableListOf(R.drawable.icon_speaker, R.drawable.icon_booking, R.drawable.icon_question, R.drawable.icon_setting)
        var icon_name_list = mutableListOf("공지사항","예약내역","FAQ","설정")

        //commuteFragment
        var ticketList = mutableListOf<TicketContent>()
    }
}