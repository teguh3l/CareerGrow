package com.notfoundteam.careergrowapp.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.notfoundteam.careergrowapp.data.model.UserModel
import com.notfoundteam.careergrowapp.data.pref.UserRepository
import kotlinx.coroutines.launch

class MainViewModel(private val repository: UserRepository): ViewModel() {

    //kode bermasalah
    fun getSession(): LiveData<UserModel> {
        return repository.getSession().asLiveData()
    }
    fun logout() {
        viewModelScope.launch {
            repository.logout()
        }
    }


}