package com.kt.digicobus.data.model

import com.google.gson.annotations.SerializedName
import java.util.*

//예약등록
data class ReserveRegister(
    // 예약할 날짜 (ex. 2022-12-08)
    @SerializedName("reserveDate")
    val reserveDate: String,
    // 예약하는 버스 id
    @SerializedName("busId")
    val busId: Int,
    // 예약하는 노선 id
    @SerializedName("stationId")
    val stationId:Int,
)

//예약조회
data class ReserveSearch(
    // 예약내역 id
    @SerializedName("reservation_id")
    val reservationId:Int,

    // 예약한 날짜 (ex. 2022-12-07)
    @SerializedName("reserve_date")
    val reserveDate: String,

    // 데이터베이스에 저장된 노선의 id
    @SerializedName("station_id")
    val stationId:Int,

    // 출발 주요 역 (ex. 간선오거리역)
    @SerializedName("main_place")
    val mainPlace:String,

    // 출발 세부 장소 (ex. 1번출구 버스정류장 앞)
    @SerializedName("detail_place")
    val detailPlace:String,

    // 출발 시간 (ex. 06:30)
    @SerializedName("departure_time")
    val departureTime:String,

    // 사옥위치 (ex. 판교사옥)
    @SerializedName("office_place")
    val officePlace:String,

    // 사옥시간 (ex. 08:30)
    @SerializedName("office_time")
    val officeTime:String,

    // bus_id : 해당 노선을 지나가는 버스 id
    @SerializedName("bus_id")
    val busId:Int,

    // 차량 번호 (ex. 경기71아7215)
    @SerializedName("bus_number")
    val busNumber:String,

    // 기사님 번호 (ex. 010-6684-3219)
    @SerializedName("bus_driver_number")
    val busDriverNumber:String,
)