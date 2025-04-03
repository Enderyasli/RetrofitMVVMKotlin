package com.enderyasli.retrofitcoroutines

import com.enderyasli.retrofitcoroutines.data.Post
import com.enderyasli.retrofitcoroutines.service.RetrofitInstance
import retrofit2.Response

class Repository {

    private val apiService = RetrofitInstance.api

    suspend fun getPosts(userId: Int): Response<List<Post>> {
        return apiService.getPosts(userId)

    }

}