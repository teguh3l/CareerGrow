package com.notfoundteam.careergrowapp.di

import android.content.Context
import com.notfoundteam.careergrowapp.data.api.ApiConfig
import com.notfoundteam.careergrowapp.data.pref.UserRepository
import com.notfoundteam.careergrowapp.data.pref.UserPreference
import com.notfoundteam.careergrowapp.data.pref.dataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

object Injection {
    fun provideRepository(context: Context): UserRepository {
        val preference = UserPreference.getInstance(context.dataStore)
        val user = runBlocking { preference.getSession().first() }
        val apiService = ApiConfig.getApiService(user.token)
        return UserRepository.getInstance(preference, apiService)
    }
}