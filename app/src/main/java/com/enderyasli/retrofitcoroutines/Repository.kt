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

    suspend fun getUserCommentsWithId(postId: Int): Response<List<User>> {
        return apiService.getUserCommentsWithId(postId)
    }

    suspend fun getSortedUserComments(
        postId: Int,
        sort: String,
        order: String
    ): Response<List<User>> {
        return apiService.getSortedUserComments(postId, sort, order)
    }

    suspend fun postUser(user: User): Response<User> {
        return apiService.postUser(user)
    }
}