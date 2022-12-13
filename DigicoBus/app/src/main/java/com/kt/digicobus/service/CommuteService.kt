package com.kt.digicobus.service

import com.kt.digicobus.data.model.BusEntireRoute
import com.kt.digicobus.data.model.CommuteBusInfo
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import java.util.Objects

interface CommuteService {
    //셔틀버스 정보 모두 반환
    @GET("commute-bus")
    fun selectAllCommuteBusInfo(): Call<List<CommuteBusInfo>>

    // 버스의 전체 노선 반환
    @GET("commute-bus/{busId}")
    fun selectBusEntireRoute(@Path("busId") busId: Int): Call<List<BusEntireRoute>>
}