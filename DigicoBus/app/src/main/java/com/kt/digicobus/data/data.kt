package com.kt.digicobus.data

import android.widget.TextView
import com.kt.digicobus.R
import com.kt.digicobus.data.model.CommuteBusInfo
import com.kt.digicobus.data.model.ReserveRegister

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

        // 전체 통근 노선도
        var allList = mutableListOf<CommuteBusInfo>()
        // 출근 통근 노선도
        var commuteBusInfoList = mutableListOf<CommuteBusInfo>()
        // 퇴근 통근 노선도
        var commuteLeaveBusInfoList = mutableListOf<CommuteBusInfo>()

        //선택한 출근길 값
        var choiceRoute = CommuteBusInfo()
        // 통근버스 리스트 선택 여부 확인
        var routeChoiceState = 0
        // 통근버스 예약 등록 리스트
        var busRegisterList = mutableListOf<ReserveRegister>()

        // login
        val userList = mutableListOf(UserForLogin("10153648", "1111"), UserForLogin("1","1"), UserForLogin("", ""))

        // 요일
        val dayNumToStringList = listOf("", "월", "화", "수", "목", "금", "토", "일")
    }
}