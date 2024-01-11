package com.notfoundteam.careergrowapp.data.model

data class UserModel (
    val email: String,
    val token: String,
    val isLogin: Boolean = false
)