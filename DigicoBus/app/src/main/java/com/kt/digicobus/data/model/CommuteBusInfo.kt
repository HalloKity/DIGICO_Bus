package com.kt.digicobus.data.model

import com.google.gson.annotations.SerializedName

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

data class BusEntireRoute(
    @SerializedName("busId")
    val busId: String,

    @SerializedName("stationId")
    val stationId: String,

    @SerializedName("mainPlace")
    val mainPlace: String,

    @SerializedName("detailPlace")
    val detailPlace: String,

    @SerializedName("departureTime")
    val departureTime: String,

    @SerializedName("latitude")
    val latitude: Double,

    @SerializedName("longitude")
    val longitude: Double
)