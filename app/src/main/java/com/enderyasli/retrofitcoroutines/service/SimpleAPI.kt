package com.enderyasli.retrofitcoroutines.service


import com.enderyasli.retrofitcoroutines.data.User
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface SimpleAPI {

    @GET("/posts/1")
    suspend fun getUser(): Response<User>


    @GET("/posts/{id}")
    suspend fun getUserWithId(
        @Path("id") id: Int
    ): Response<User>
}