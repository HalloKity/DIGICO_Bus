package com.kt.digicobus.service

import com.kt.digicobus.data.model.ReserveSearch
import retrofit2.Call
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Path

interface ReservationService {
    // 예약내역 반환
    @GET("reservation-bus")
    fun selectAllReservationBusInfo(): Call<List<ReserveSearch>>

    // 예약내역 취소
    @DELETE("/reservation-bus/cancel/{reservationId}")
    fun deleteReservation(@Path("reservationId") reservationId: Int): Call<Void>
}