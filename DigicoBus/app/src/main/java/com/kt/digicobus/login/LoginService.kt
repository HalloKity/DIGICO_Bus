package com.kt.digicobus.login

import com.kt.digicobus.data.UserForLogin
import com.kt.digicobus.data.data

class LoginService {
    private val userList = data.userList

    fun requestLogin(userId:String, userPw:String) : Boolean {
        val cnt = userList.count { u: UserForLogin -> u.id == userId && u.pw == userPw }
        return cnt == 1
    }
}