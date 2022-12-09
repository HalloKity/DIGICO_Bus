package com.kt.digicobus.data.model

data class CommuteBusInfo(
    val stationId: Int? = -1,
    val busId: Int? = -1,
    val commuteId:Int? = -1,
    val line:String? = "",
    val mainPlace:String? = "",
    val detailPlace:String? = "",
    val departureTime:String? = "",
    val officePlace:String? = "",
    val officeTime:String? = "",
    val latitude:Double? = 0.0,
    val longitude:Double? = 0.0,
    // 즐겨찾기 여부 default false
    val isFavorite: Boolean = false,
    // 클릭 여부 default false
    val isClick: Boolean = false,
)
