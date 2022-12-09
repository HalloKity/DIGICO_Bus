package com.kt.digicobus.data

import android.widget.TextView
import com.kt.digicobus.R

data class TicketContent(
    var line: String,
    var mainPlace: String,
    var detailPlace: String,
    var departureTime: String,
    var isFavorite: Boolean,
    var isClick: Boolean
)

data class BusStopContent(
    var busStopLocation:String,
    var departureTime:String,
    var locationLatitude:Double,
    var locationLongitude:Double,
    var isClick: Boolean
)

data class UserForLogin(
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
        val userList = mutableListOf(UserForLogin("10150000", "1234"), UserForLogin("1","1"), UserForLogin("", ""))

        var busChoiceInfo = -1
    }
}