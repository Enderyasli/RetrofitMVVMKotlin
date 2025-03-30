package com.enderyasli.retrofitcoroutines.service

import com.enderyasli.retrofitcoroutines.utils.Constants.BASE_URL
import com.google.gson.Gson
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

object RetrofitInstance {

    private val retrofit by lazy {

        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    }

    val api: SimpleAPI by lazy {
        retrofit.create()
    }
}