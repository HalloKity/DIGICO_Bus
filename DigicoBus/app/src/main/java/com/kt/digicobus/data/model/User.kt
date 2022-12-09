package com.kt.digicobus.data.model

data class User(
    //유저 프로필 이미지
    val profileImg: String?,
    //유저 이름
    val name: String,
    //유저 소속 부문
    val department:String?,
    //유저 소속 본부 & 팀
    val team:String?,
)