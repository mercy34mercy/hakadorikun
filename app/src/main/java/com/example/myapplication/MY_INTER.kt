package com.example.myapplication

import retrofit2.Call
import retrofit2.http.*

interface MyService2 {
    @GET("posts")
    fun getRawResponseForPosts(): Call<List<Post>>

    @POST("posts")
    fun postRawRequestForPosts(@Body post: Post): Call<Post>

    @PUT("posts/{id}")
    fun putRawRequestForPosts(@Path("id") id: String, @Body post: Post): Call<Post>

    @DELETE("posts/{id}")
    fun deletePathParam(@Path("id") id: String): Call<Post>

}