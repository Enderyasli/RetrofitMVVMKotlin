package com.enderyasli.retrofitcoroutines.service


import com.enderyasli.retrofitcoroutines.data.User
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface SimpleAPI {

    @GET("/posts/1")
    suspend fun getUser(): Response<User>


    @GET("/posts/{id}")
    suspend fun getUserWithId(
        @Path("id") id: Int
    ): Response<User>


    @GET("/posts/1/comments")
    suspend fun getUserComments(): Response<List<User>>

    @GET("/posts/{postId}/comments")
    suspend fun getUserCommentsWithId(@Path("postId") postId: Int): Response<List<User>>

    @GET("/comments")
    suspend fun getSortedUserComments(
        @Query("postId") postId: Int,
        @Query("_sort") sort: String,
        @Query("_order") order: String,
    ): Response<List<User>>

    @POST("/posts")
    suspend fun postUser(
        @Body user: User
    ): Response<User>
}