package com.notfoundteam.careergrowapp.ui.register

import androidx.lifecycle.ViewModel
import com.notfoundteam.careergrowapp.data.pref.UserRepository

class RegisterViewModel (private val repository: UserRepository): ViewModel(){

    fun register (
        name: String,
        email: String,
        password: String,
    ) = repository.register(name, email, password)

}