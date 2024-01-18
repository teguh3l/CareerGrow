package com.notfoundteam.careergrowapp.data.model

import com.notfoundteam.careergrowapp.data.response.Data

data class UserModel (
    val email: String,
    val token: String,
    val isLogin: Boolean = false
) {
    companion object {
        fun fromData(data: Data): UserModel {
            return UserModel(
                email = data.email,
                token = data.token,
                isLogin = true
            )
        }
    }
}