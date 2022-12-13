package com.kt.digicobus.service

import retrofit2.Call
import com.kt.digicobus.data.model.ReserveSearch
import retrofit2.http.GET

interface ReservationService {
    // 예약내역 반환
    @GET("reservation-bus")
    fun selectAllReservationBusInfo(): Call<List<ReserveSearch>>
}