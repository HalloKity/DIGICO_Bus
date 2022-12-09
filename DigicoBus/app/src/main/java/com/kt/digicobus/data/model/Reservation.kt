package com.kt.digicobus.data.model

import java.util.*

//예약등록
data class ReserveRegister(
    // 예약할 날짜 (ex. 2022-12-08)
    val reserveDate: String,
    // 예약하는 버스 id
    val busId: Int,
    // 예약하는 노선 id
    val stationId:Int,
)

//예약조회
data class ReserveSearch(
    // 예약내역 id
    val reservationId:Int,
    // 예약한 날짜 (ex. 2022-12-07)
    val reserveDate: Date,
    // 데이터베이스에 저장된 노선의 id
    val stationId:Int,
    // 출발 주요 역 (ex. 간선오거리역)
    val mainPlace:String,
    // 출발 세부 장소 (ex. 1번출구 버스정류장 앞)
    val detailPlace:String,
    // 출발 시간 (ex. 06:30)
    val departureTime:String,
    // 사옥위치 (ex. 판교사옥)
    val officePlace:String,
    // 사옥시간 (ex. 08:30)
    val officeTime:String,
    // bus_id : 해당 노선을 지나가는 버스 id
    val busId:Int,
    // 차량 번호 (ex. 경기71아7215)
    val busNumber:String,
    // 기사님 번호 (ex. 010-6684-3219)
    val busDriverNumber:String,
)