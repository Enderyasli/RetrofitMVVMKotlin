package com.enderyasli.retrofitcoroutines.service


import com.enderyasli.retrofitcoroutines.data.User
import com.enderyasli.retrofitcoroutines.utils.Resource
import retrofit2.Response
import retrofit2.http.GET

interface SimpleAPI {

    @GET("/posts/1")
    suspend fun getUser() : Response<User>
}