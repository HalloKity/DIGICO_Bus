package com.kt.digicobus.service

import com.kt.digicobus.data.model.CommuteBusInfo
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST
import java.util.Objects

interface CommuteService {
    //셔틀버스 정보 모두 반환
    @GET("commute-bus")
    fun selectAllCommuteBusInfo(): Call<List<CommuteBusInfo>>

}