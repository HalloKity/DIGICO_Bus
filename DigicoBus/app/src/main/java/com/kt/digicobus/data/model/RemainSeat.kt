package com.kt.digicobus.data.model

import com.google.gson.annotations.SerializedName

data class RemainSeat(
    @SerializedName("date")
    val date: String,
    @SerializedName("remain_seats_count")
    val remainSeatsCount: Int,
)