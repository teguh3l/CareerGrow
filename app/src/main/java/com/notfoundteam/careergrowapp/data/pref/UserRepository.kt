package com.notfoundteam.careergrowapp.data.pref

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.notfoundteam.careergrowapp.data.api.ApiService
import com.notfoundteam.careergrowapp.data.model.UserModel
import com.notfoundteam.careergrowapp.data.response.LoginResponse
import com.notfoundteam.careergrowapp.data.response.RegisterResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.HttpException

class UserRepository private constructor(
    private val userPreference: UserPreference,
    private val apiService: ApiService,
) {
    fun getSession(): Flow<UserModel> {
        return userPreference.getSession()
    }

    suspend fun saveSession(user: UserModel) {
        userPreference.saveSession(user)
    }

    fun register(
        nama: String,
        email: String,
        password: String,
        conf_password: String
    ): LiveData<ResultState<RegisterResponse>> = liveData {
        emit(ResultState.Loading)
        try {
            val requestBody = JsonObject().apply {
                addProperty("nama", nama)
                addProperty("email", email)
                addProperty("password", password)
                addProperty("conf_password", conf_password)
            }

            val response = apiService.register("application/json", requestBody)

            if (response.isSuccessful) {
                val responseBody = response.body()

                if (responseBody != null) {
                    emit(ResultState.Success(responseBody))
                }
                else {
                    emit(ResultState.Error("Response body is null"))
                }
            }
            else {
                emit(ResultState.Error("Registration failed: ${response.message()}"))
            }

        } catch (e: HttpException) {
            emit(ResultState.Error("Registration failed: ${e.message}"))
        }
    }

    fun login(email: String, password: String) = liveData {
        emit(ResultState.Loading)
        try {
            val requestBody = JsonObject().apply {
                addProperty("email", email)
                addProperty("password", password)
            }

            val response = apiService.login("application/json", requestBody)

            if (response.isSuccessful) {
                val responseBody = response.body()

                if (responseBody != null) {
                    //convert LoginResponse to UserModel
                    val userModel = UserModel.fromData(responseBody.data)
                    saveSession(userModel)
                    emit(ResultState.Success(responseBody))
                } else {
                    emit(ResultState.Error("Response body is null"))
                }
            } else {
                val errorResponse = response.errorBody()?.string()
                emit(ResultState.Error("Login failed: $errorResponse"))
            }

        } catch (e: HttpException) {
            // Handle HTTP exception
            val errorBody = parseErrorBody<LoginResponse>(e)
            emit(ResultState.Error(errorBody.message))
        } catch (e: Exception) {
            // Handle other exceptions
            emit(ResultState.Error("Login failed: ${e.message}"))
        }
    }

    private inline fun <reified T> parseErrorBody(e: HttpException): T {
        val jsonInString = e.response()?.errorBody()?.string()
        return Gson().fromJson(jsonInString, T::class.java)
    }

    suspend fun logout() {
        userPreference.logout()
    }

    companion object {
        @Volatile
        private var instance: UserRepository? = null
        fun getInstance(
            userPreference: UserPreference,
            apiService: ApiService,
        ): UserRepository =
            instance ?: synchronized(this) {
                instance ?: UserRepository(userPreference, apiService)
            }.also { instance = it }
    }
}
