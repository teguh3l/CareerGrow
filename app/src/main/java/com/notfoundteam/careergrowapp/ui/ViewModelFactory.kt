package com.notfoundteam.careergrowapp.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.notfoundteam.careergrowapp.data.pref.UserRepository
import com.notfoundteam.careergrowapp.di.Injection
import com.notfoundteam.careergrowapp.ui.login.LoginViewModel
import com.notfoundteam.careergrowapp.ui.main.MainViewModel
import com.notfoundteam.careergrowapp.ui.register.RegisterViewModel

class ViewModelFactory (private val repository: UserRepository):
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(MainViewModel::class.java) -> {
                MainViewModel(repository) as T
            }
            modelClass.isAssignableFrom(RegisterViewModel::class.java) -> {
                RegisterViewModel(repository) as T
            }
            modelClass.isAssignableFrom(LoginViewModel::class.java) -> {
                LoginViewModel(repository) as T
            }

            else ->
                throw IllegalArgumentException("Unknown ViewModel class : " +modelClass.name)
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: ViewModelFactory? = null

        @JvmStatic
        fun getInstance(context: Context): ViewModelFactory =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: ViewModelFactory(Injection.provideRepository(context))
            }.also { INSTANCE = it }
    }
}