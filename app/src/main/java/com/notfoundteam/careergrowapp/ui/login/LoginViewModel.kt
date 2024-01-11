package com.notfoundteam.careergrowapp.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.notfoundteam.careergrowapp.data.model.UserModel
import com.notfoundteam.careergrowapp.data.pref.UserRepository
import kotlinx.coroutines.launch

class LoginViewModel (private val repository: UserRepository): ViewModel(){

    fun login(email: String, password: String) = repository.login(email, password)

    fun saveSession(user: UserModel) {
        viewModelScope.launch {
            repository.saveSession(user)
        }
    }

}