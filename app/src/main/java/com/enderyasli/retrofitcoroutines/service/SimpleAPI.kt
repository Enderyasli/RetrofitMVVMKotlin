package com.enderyasli.retrofitcoroutines.service


import com.enderyasli.retrofitcoroutines.data.Post
import retrofit2.Response
import retrofit2.http.GET

interface SimpleAPI {

    @GET("/posts")
    suspend fun getPosts(): Response<List<Post>>

}