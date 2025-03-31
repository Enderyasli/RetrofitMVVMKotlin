package com.enderyasli.retrofitcoroutines

import com.enderyasli.retrofitcoroutines.data.User
import com.enderyasli.retrofitcoroutines.service.RetrofitInstance
import retrofit2.Response

class Repository {

    private val apiService = RetrofitInstance.api

    suspend fun getUser(): Response<User> {
        return apiService.getUser()
    }

    suspend fun getUserWithId(id: Int): Response<User> {
        return apiService.getUserWithId(id)
    }
}