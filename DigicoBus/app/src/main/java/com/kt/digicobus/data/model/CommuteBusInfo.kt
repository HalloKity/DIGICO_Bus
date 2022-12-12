package com.kt.digicobus.data.model

data class CommuteBusInfo(
    val stationId: String = "",
    val busId: String = "",
    val commuteId:String = "",
    val line:String = "",
    val mainPlace:String = "",
    val detailPlace:String = "",
    val departureTime:String? = "",
    val officePlace:String? = "",
    val officeTime:String? = "",
    val latitude:String? = "",
    val longitude:String? = "",
    // 즐겨찾기 여부 default false
    var isFavorite: Boolean = false,
    // 클릭 여부 default false
    var isClick: Boolean = false,
)
