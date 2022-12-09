package com.kt.digicobus.data.model

data class CommuteBusInfo(
    val stationId: Int?,
    val busId: Int?,
    val commuteId:Int?,
    val line:String?,
    val mainPlace:String?,
    val detailPlace:String?,
    val departureTime:String?,
    val officePlace:String?,
    val officeTime:String?,
    val latitude:Double?,
    val longitude:Double?,
    // 즐겨찾기 여부 default false
    val isFavorite: Boolean = false,
    // 클릭 여부 default false
    val isClick: Boolean = false,
)
