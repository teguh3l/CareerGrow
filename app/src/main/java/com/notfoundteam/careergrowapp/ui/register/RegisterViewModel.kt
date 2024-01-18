package com.notfoundteam.careergrowapp.ui.register

import androidx.lifecycle.ViewModel
import com.notfoundteam.careergrowapp.data.pref.UserRepository

class RegisterViewModel (private val repository: UserRepository): ViewModel(){

    fun register (
        nama: String,
        email: String,
        password: String,
        conf_password: String,
    ) = repository.register(nama, email, password, conf_password)

}