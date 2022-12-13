package com.kt.digicobus.service

import com.kt.digicobus.data.model.ReserveRegister
import retrofit2.Call
import com.kt.digicobus.data.model.ReserveSearch
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ReservationService {
    // 예약내역 반환
    @GET("reservation-bus")
    fun selectAllReservationBusInfo(): Call<List<ReserveSearch>>

    // 예약 등록
    @POST("reservation-bus")
    fun insertReservationBusInfo(@Body reserveRegisterList: List<ReserveRegister>) : Call<Int>
}