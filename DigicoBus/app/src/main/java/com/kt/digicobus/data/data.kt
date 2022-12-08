package com.kt.digicobus.data

import android.widget.TextView
import com.kt.digicobus.R

data class TicketContent(
    var tv_line: String,
    var tv_main_place: String,
    var tv_detail_place: String,
    var tv_departure_time: String,
    var isFavorite: Boolean,
)

data class BusStopContent(
    var tv_bus_stop_location:String,
    var tv_departure_time:String,
    var tv_location_latitude:Double,
    var tv_location_longitude:Double,
    var isClick: Boolean
)

data class User(
    val id: String,
    val pw: String
)

class data{
    companion object{
        //MoreFragment
        val icon_list = mutableListOf(R.drawable.icon_speaker, R.drawable.icon_booking, R.drawable.icon_question, R.drawable.icon_setting)
        var icon_name_list = mutableListOf("공지사항","예약내역","FAQ","설정")

        //commuteFragment
        var ticketList = mutableListOf<TicketContent>()

        //commuteBusChoiceFragment
        var busStopList = mutableListOf<BusStopContent>()

        // login
        val userList = mutableListOf(User("10150000", "1234"), User("1","1"), User("", ""))

        var busChoiceInfo = -1
    }
}