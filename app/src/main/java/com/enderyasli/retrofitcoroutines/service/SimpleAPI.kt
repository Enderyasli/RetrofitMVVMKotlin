package com.enderyasli.retrofitcoroutines.service


import com.enderyasli.retrofitcoroutines.data.Post
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface SimpleAPI {

    @GET("/posts")
    suspend fun getPosts(
        @Query("userId") userId: Int
    ): Response<List<Post>>

}